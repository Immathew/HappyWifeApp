package com.example.happywifeapp.upcomingEvents

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.happywifeapp.database.EventDatabaseDAO
import java.lang.IllegalArgumentException


class UpcomingEventsViewModelFactory(
        private val dataSource: EventDatabaseDAO,
        private val application: Application) : ViewModelProvider.Factory {

        override fun <T: ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UpcomingEventsViewModel::class.java)) {
                return UpcomingEventsViewModel(dataSource, application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
}