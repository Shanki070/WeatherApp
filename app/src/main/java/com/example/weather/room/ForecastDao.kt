package com.example.weather.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ForecastDao {

    @Query("SELECT * FROM forecast WHERE lat = :lat AND lon = :lon GROUP BY date")
    fun getForecast(lat: Double, lon: Double): LiveData<List<Forecast>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherDetails(forecast: Forecast): Long

    @Query("SELECT MAX(date) FROM forecast WHERE lat = :lat AND lon = :lon")
    fun getForecastMaxDate(lat: Double, lon: Double): Long

    @Query("SELECT COUNT(*) FROM forecast WHERE lat = :lat AND lon = :lon")
    fun getListCount(lat: Double, lon: Double): Long
}