package com.example.happywifeapp.di

import android.content.Context
import androidx.room.Room
import com.example.happywifeapp.database.EventDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            EventDatabase::class.java,
            "event_history_database"
        ).build()

    @Singleton
    @Provides
    fun provideDao(database: EventDatabase) = database.eventDatabaseDAO()
}