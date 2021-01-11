package com.example.happywifeapp.database

import androidx.lifecycle.LiveData

class EventRepository(private val eventDao: EventDatabaseDAO) {

    val readAllData: LiveData<List<Event>> = eventDao.getAllEvents()

    suspend fun addEvent (event: Event) {
        eventDao.insertEvent(event)
    }
}