package com.example.happywifeapp



import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.happywifeapp.database.Event
import com.example.happywifeapp.database.EventDatabase
import com.example.happywifeapp.database.EventDatabaseDAO

import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private lateinit var eventDao: EventDatabaseDAO
    private lateinit var db: EventDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, EventDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        eventDao = db.eventDatabaseDAO()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    suspend fun insertAndGetEvent() {
        val event = Event(1, "nowy","12381273617.jpg", "opis",
            "01-02-2020", "poz")
        eventDao.insertEvent(event)

        val newestEvent = eventDao.getEvent(1)
        assertEquals(newestEvent.eventId, 1)
    }
}