package com.example.happywifeapp.database

import androidx.lifecycle.LiveData
import java.util.*


class EventRepository(private val eventDao: EventDatabaseDAO) {

    private var calendar = Calendar.getInstance()

    private var getActualMonth = (calendar.get(Calendar.MONTH) + 1)
    private var getNextMonth = (calendar.get(Calendar.MONTH)+2)

    private val currentMonth = setupCorrectMonthForDatabase(getActualMonth)

    private val nextMonth = setupCorrectMonthForDatabase(getNextMonth)

    val readAllData: LiveData<List<Event>> = eventDao.getAllEvents()

    val readThisMonthEvents: LiveData<List<Event>> = eventDao.getAllEventsInThisMonth(currentMonth)

    val readNextMonthEvents: LiveData<List<Event>> = eventDao.getAllEventsInNextMonth(nextMonth)

    private fun setupCorrectMonthForDatabase(monthInCalendar : Int): String {
        return if (monthInCalendar <10){
            "-0$monthInCalendar"
        }else "-$monthInCalendar"
    }


}