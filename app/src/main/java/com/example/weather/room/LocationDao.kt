package com.example.weather.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: Location): Long

    @Query("SELECT * FROM location")
    fun getAllLocations(): List<Location>?

    @Query("SELECT * FROM location")
    fun getAllLocationsLive(): LiveData<List<Location>?>
}