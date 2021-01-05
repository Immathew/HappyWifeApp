package com.example.happywifeapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events_to_remember_table")
data class Event(
        @PrimaryKey(autoGenerate = true)
        val eventId: Int = 0,
        @ColumnInfo(name = "title")
        val title: String,
        @ColumnInfo(name = "image")
        val image: String,
        @ColumnInfo(name = "description")
        val description: String,
        @ColumnInfo(name = "date")
        val date: String,
        @ColumnInfo(name = "location")
        val location: String,
        @ColumnInfo(name = "latitude")
        val latitude: Double,
        @ColumnInfo(name = "longitude")
        val longitude: Double
)
