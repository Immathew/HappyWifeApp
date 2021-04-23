package com.example.happywifeapp.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Event::class],
    version = 2,
    exportSchema = false
)
abstract class EventDatabase : RoomDatabase() {

    abstract fun eventDatabaseDAO(): EventDatabaseDAO

}