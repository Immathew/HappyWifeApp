package com.example.happywifeapp.database

import androidx.lifecycle.LiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import java.util.*
import javax.inject.Inject

@ViewModelScoped
class EventRepository @Inject constructor(private val eventDao: EventDatabaseDAO) {

    private var calendar = Calendar.getInstance()

    private var getActualMonth = (calendar.get(Calendar.MONTH) + 1)
    private var getNextMonth = (calendar.get(Calendar.MONTH) + 2)

    private val currentMonth = setupCorrectMonthForDatabase(getActualMonth)

    private val nextMonth = setupCorrectMonthForDatabase(getNextMonth)

    fun readAllData(): LiveData<List<Event>> = eventDao.getAllEvents()

    fun getEvent(key: Int): Event = eventDao.getEvent(key)

    fun readThisMonthEvents(): LiveData<List<Event>> =
        eventDao.getAllEventsInThisMonth(currentMonth)

    fun readNextMonthEvents(): LiveData<List<Event>> =
        eventDao.getAllEventsInNextMonth(nextMonth)

    suspend fun insertEvent(event: Event) = eventDao.insertEvent(event)

    suspend fun updateEvent(event: Event) = eventDao.updateEvent(event)

    suspend fun deleteEvent(event: Event) = eventDao.deleteEvent(event)

    private fun setupCorrectMonthForDatabase(monthInCalendar: Int): String {
        return if (monthInCalendar < 10) {
            "-0$monthInCalendar"
        } else "-$monthInCalendar"
    }


}