package com.example.happywifeapp.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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

}