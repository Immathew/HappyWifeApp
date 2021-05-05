package com.example.happywifeapp.di

import android.content.Context
import androidx.room.Room
import com.example.happywifeapp.database.EventDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Singleton
    @Provides
    @Named("TestDb")
    fun provideInMemoryDB(
        @ApplicationContext context: Context
    ) = Room.inMemoryDatabaseBuilder(
        context,
        EventDatabase::class.java
    ).allowMainThreadQueries()
        .build()

    @Singleton
    @Provides
    @Named("TestDao")
    fun providesTestDao(database: EventDatabase) = database.eventDatabaseDAO()
}