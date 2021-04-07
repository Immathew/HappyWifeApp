package com.example.happywifeapp.allEvents


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.happywifeapp.database.Event
import com.example.happywifeapp.database.EventDatabase
import com.example.happywifeapp.database.EventDatabaseDAO
import com.example.happywifeapp.database.EventRepository

class AllEventsListViewModel(
    val database: EventDatabaseDAO,
    application: Application
) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Event>>

    private val repository: EventRepository


    init {
        val eventDao = EventDatabase.getInstance(application).eventDatabaseDAO()
        repository = EventRepository(eventDao)
        readAllData = repository.readAllData()
    }
}