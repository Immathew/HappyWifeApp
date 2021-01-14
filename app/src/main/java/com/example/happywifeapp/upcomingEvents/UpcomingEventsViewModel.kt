package com.example.happywifeapp.upcomingEvents

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.happywifeapp.database.Event
import com.example.happywifeapp.database.EventDatabase
import com.example.happywifeapp.database.EventDatabaseDAO
import com.example.happywifeapp.database.EventRepository

class UpcomingEventsViewModel(
        val database: EventDatabaseDAO,
        application: Application) : AndroidViewModel(application) {

     val getEventsInThisMonth: LiveData<List<Event>>
     private val repository: EventRepository

     init {
         val eventDao = EventDatabase.getInstance(application).eventDatabaseDAO()
         repository = EventRepository(eventDao)
         getEventsInThisMonth = repository.readThisMonthEvents
     }

}