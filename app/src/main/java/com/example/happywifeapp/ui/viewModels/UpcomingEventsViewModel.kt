package com.example.happywifeapp.ui.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.happywifeapp.database.Event
import com.example.happywifeapp.database.EventDatabase
import com.example.happywifeapp.database.EventDatabaseDAO
import com.example.happywifeapp.database.EventRepository

class UpcomingEventsViewModel(
        val database: EventDatabaseDAO,
        application: Application) : AndroidViewModel(application) {

     val getEventsInThisMonth: LiveData<List<Event>>
     val getEventInNextMonth: LiveData<List<Event>>

     private val repository: EventRepository

    var toggleFabButton = false

     init {
         val eventDao = EventDatabase.getInstance(application).eventDatabaseDAO()
         repository = EventRepository(eventDao)
         getEventsInThisMonth = repository.readThisMonthEvents()
         getEventInNextMonth = repository.readNextMonthEvents()
     }

    fun setupCorrectFab() {
        if (!toggleFabButton) {
            toggleFabButton = true
        } else if (toggleFabButton) {
            toggleFabButton = false
        }
    }
}