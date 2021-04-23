package com.example.happywifeapp.ui.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.happywifeapp.database.Event
import com.example.happywifeapp.database.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpcomingEventsViewModel @Inject constructor(
    private val repository: EventRepository,
    application: Application
) : AndroidViewModel(application) {

    val getEventsInThisMonth: LiveData<List<Event>> = repository.readThisMonthEvents()
    val getEventInNextMonth: LiveData<List<Event>> = repository.readNextMonthEvents()

    var toggleFabButton = false

    fun setupCorrectFab() {
        if (!toggleFabButton) {
            toggleFabButton = true
        } else if (toggleFabButton) {
            toggleFabButton = false
        }
    }
}