package com.example.happywifeapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EventDatabaseDAO {

    @Insert
    suspend fun insertEvent (event: Event)

    @Update
    fun updateEvent(event: Event)

    @Delete
    fun deleteEvent(event: Event)

    @Query("SELECT * FROM events_to_remember_table WHERE eventId= :key")
    fun getEvent(key: Int): Event

    @Query("SELECT * FROM events_to_remember_table ORDER BY date ASC")
    fun getAllEvents(): LiveData<List<Event>>
}
