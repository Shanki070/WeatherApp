package com.example.weather.screens.locationList

import com.example.weather.room.LocationDao
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class LocationListRepository @Inject constructor(private val locationDao: LocationDao) {

    fun getAllLocations() = locationDao.getAllLocations()
}
