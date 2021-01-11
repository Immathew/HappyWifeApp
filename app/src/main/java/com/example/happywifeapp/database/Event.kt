package com.example.happywifeapp.database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



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
        @ColumnInfo(name = "latitude")
        val latitude: Double,
        @ColumnInfo(name = "longitude")
        val longitude: Double
): Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readInt(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readDouble(),
                parcel.readDouble()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeInt(eventId)
                parcel.writeString(title)
                parcel.writeString(image)
                parcel.writeString(description)
                parcel.writeString(date)
                parcel.writeString(location)
                parcel.writeDouble(latitude)
                parcel.writeDouble(longitude)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<Event> {
                override fun createFromParcel(parcel: Parcel): Event {
                        return Event(parcel)
                }

                override fun newArray(size: Int): Array<Event?> {
                        return arrayOfNulls(size)
                }
        }
}
