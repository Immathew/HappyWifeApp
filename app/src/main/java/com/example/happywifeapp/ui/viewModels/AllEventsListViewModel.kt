package com.example.happywifeapp.ui.viewModels


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.happywifeapp.database.Event
import com.example.happywifeapp.database.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllEventsListViewModel @Inject constructor(
    private val repository: EventRepository,
    application: Application
) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Event>> = repository.readAllData()

    fun getEvent(key: Int) : Event {
       return repository.getEvent(key)
    }

    fun deleteEvent(event: Event) =
        viewModelScope.launch(Dispatchers.IO) { repository.deleteEvent(event) }


}