package com.example.happywifeapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EventDatabaseDAO {

    @Insert
    suspend fun insertEvent (event: Event)

    @Update
    suspend fun updateEvent(event: Event)

    @Delete
    suspend fun deleteEvent(event: Event)

    @Query("SELECT * FROM events_to_remember_table WHERE date LIKE '%' ||  :currentMonth  || '%' ORDER BY date ASC")
    fun getAllEventsInThisMonth(currentMonth: String): LiveData<List<Event>>

    @Query("SELECT * FROM events_to_remember_table WHERE date LIKE '%' || :nextMonth || '%' ORDER BY date ASC")
    fun getAllEventsInNextMonth(nextMonth: String): LiveData<List<Event>>

    @Query("SELECT * FROM events_to_remember_table WHERE eventId= :key")
    fun getEvent(key: Int): Event

    @Query("SELECT * FROM events_to_remember_table ORDER BY eventId DESC")
    fun getAllEvents(): LiveData<List<Event>>


}
