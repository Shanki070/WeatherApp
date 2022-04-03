package com.example.weather.room

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weather.util.DateUtil

@Entity
data class Forecast(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val lat: Double,
    val lon: Double,
    val date: Long,
    @ColumnInfo(name = "temperature_min") val minTemperature: Double,
    @ColumnInfo(name = "temperature_max") val maxTemperature: Double,
    @ColumnInfo(name = "weather_icon") val weatherIcon: String,
    val description: String,
) {
    fun getTime() = DateUtil.getTimeWithoutDate(date)

    fun getMinMaxTemp() = "${minTemperature.toInt()}C - ${maxTemperature.toInt()}C"
}