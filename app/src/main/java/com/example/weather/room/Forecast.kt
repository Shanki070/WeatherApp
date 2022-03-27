package com.example.weather.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class Forecast(
    @PrimaryKey(autoGenerate = true) val id: String,
    val name: String,
    val lat: Double,
    val lon: Double,
    val date: Long,
    @ColumnInfo(name = "temperature_min") val minTemperature: Long,
    @ColumnInfo(name = "temperature_max") val maxTemperature: Long,
    @ColumnInfo(name = "weather_icon") val weatherIcon: String,
    val description: String,
)