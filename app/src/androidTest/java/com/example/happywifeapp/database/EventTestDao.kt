package com.example.happywifeapp.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.happywifeapp.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@HiltAndroidTest
class EventTestDao {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var instantTaskExecRule = InstantTaskExecutorRule()

    @Inject
    @Named("TestDb")
    lateinit var database: EventDatabase

    @Inject
    @Named("TestDao")
    lateinit var dao: EventDatabaseDAO

    @Before
    fun setupDb() {
        hiltRule.inject()
        dao = database.eventDatabaseDAO()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun insertEvent_readEvent() = runBlockingTest {
        // Given Event object
        val sampleEvent = Event(
            1,
            "sampleTitle",
            "sampleImage",
            "sampleDescription",
            "01-01-2001",
            "sampleLocation"
        )

        // When
        dao.insertEvent(sampleEvent)

        // Then

       val newEvent =  dao.getEvent(1)

        assertThat(newEvent).isEqualTo(sampleEvent)
    }

    @Test
    fun insert_updateEvent() = runBlockingTest {
        // Given Event object
        val sampleEvent = Event(
            1,
            "sampleTitle",
            "sampleImage",
            "sampleDescription",
            "01-01-2001",
            "sampleLocation"
        )

        // When
        dao.insertEvent(sampleEvent)

        // Then

        val updatedSampleEvent = Event(
            1,
            "updatedSampleTitle",
            "updatedSampleImage",
            "sampleDescription",
            "01-01-2001",
            "sampleLocation"
        )
        dao.updateEvent(updatedSampleEvent)

        val readUpdatedEvent = dao.getEvent(1)

        assertThat(readUpdatedEvent.title).isEqualTo(updatedSampleEvent.title)
        assertThat(readUpdatedEvent.image).isEqualTo(updatedSampleEvent.image)
    }

    @Test
    fun readEventsByMonth() = runBlockingTest {
        // Given Event object
        val sampleEvent = Event(
            1,
            "sampleTitle",
            "sampleImage",
            "sampleDescription",
            "01-01-2001",
            "sampleLocation"
        )
        val sampleEventTwo = Event(
            2,
            "sampleTitle",
            "sampleImage",
            "sampleDescription",
            "01-02-2001",
            "sampleLocation"
        )
        val sampleEventThree = Event(
            3,
            "sampleTitle",
            "sampleImage",
            "sampleDescription",
            "01-03-2001",
            "sampleLocation"
        )

        // When
        dao.insertEvent(sampleEvent)
        dao.insertEvent(sampleEventTwo)
        dao.insertEvent(sampleEventThree)

        // Then
        val eventsInCurrentMonth = dao.getAllEventsInThisMonth("-01").getOrAwaitValue()
        val eventsInNextMonth = dao.getAllEventsInNextMonth("-02").getOrAwaitValue()

        assertThat(eventsInCurrentMonth).contains(sampleEvent)
        assertThat(eventsInCurrentMonth).doesNotContain(sampleEventTwo)

        assertThat(eventsInNextMonth).contains(sampleEventTwo)
        assertThat(eventsInNextMonth).doesNotContain(sampleEvent)
        assertThat(eventsInNextMonth).doesNotContain(sampleEventThree)
    }

    @Test
    fun readAll_deleteOneEvent() = runBlockingTest {
        // Given Event object
        val sampleEvent = Event(
            1,
            "sampleTitle",
            "sampleImage",
            "sampleDescription",
            "01-01-2001",
            "sampleLocation"
        )
        val sampleEventTwo = Event(
            2,
            "sampleTitle",
            "sampleImage",
            "sampleDescription",
            "01-02-2001",
            "sampleLocation"
        )
        val sampleEventThree = Event(
            3,
            "sampleTitle",
            "sampleImage",
            "sampleDescription",
            "01-03-2001",
            "sampleLocation"
        )

        // When
        dao.insertEvent(sampleEvent)
        dao.insertEvent(sampleEventTwo)
        dao.insertEvent(sampleEventThree)

        // Then

        val readAllEvents = dao.getAllEvents().getOrAwaitValue()

        assertThat(readAllEvents).containsExactly(sampleEvent, sampleEventTwo, sampleEventThree)

        // Delete one
        dao.deleteEvent(sampleEvent)

        val readAfterDelete = dao.getAllEvents().getOrAwaitValue()

        assertThat(readAfterDelete).doesNotContain(sampleEvent)
    }

}