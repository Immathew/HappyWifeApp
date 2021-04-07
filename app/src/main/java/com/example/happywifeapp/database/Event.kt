package com.example.happywifeapp.database


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "events_to_remember_table")
data class Event(
        @PrimaryKey(autoGenerate = true)
        val eventId: Int = 0,
        @ColumnInfo(name = "title")
        val title: String?,
        @ColumnInfo(name = "image")
        val image: String?,
        @ColumnInfo(name = "description")
        val description: String?,
        @ColumnInfo(name = "date")
        val date: String?,
        @ColumnInfo(name = "location")
        val location: String?,

): Parcelable
