package com.example.weather.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

const val DATABASE_NAME = "weather-db"

@Database(entities = [Location::class, Forecast::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun forecastDao(): ForecastDao
    abstract fun locationDao(): LocationDao

    companion object {

        fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}
