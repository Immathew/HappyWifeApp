package com.example.happywifeapp.allEvents


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.happywifeapp.database.Event
import com.example.happywifeapp.database.EventDatabase
import com.example.happywifeapp.database.EventDatabaseDAO
import com.example.happywifeapp.database.EventRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllEventsListViewModel(
        val database: EventDatabaseDAO,
        application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Event>>
    //val getEventsInThisAndNextMonth: LiveData<List<Event>>
    private val repository: EventRepository

init {
    val eventDao = EventDatabase.getInstance(application).eventDatabaseDAO()
    repository = EventRepository(eventDao)
    readAllData = repository.readAllData
    //getEventsInThisAndNextMonth = repository.getEventsInThisAndNextMonth
}

}