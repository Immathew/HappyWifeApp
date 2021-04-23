package com.example.happywifeapp.ui.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happywifeapp.database.Event
import com.example.happywifeapp.database.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateEventViewModel @Inject constructor(
    private val repository: EventRepository,
    application: Application
) : AndroidViewModel(application) {

    fun updateEvent(event: Event) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateEvent(event)
    }
}