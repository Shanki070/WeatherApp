package com.example.weather.screens.addLocation

import com.example.weather.network.LocationApiService
import com.example.weather.network.NetworkConstant
import com.example.weather.room.Location
import com.example.weather.room.LocationDao
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class AddLocationRepository @Inject constructor(private val locationApiService: LocationApiService,
                                                private val  locationDao: LocationDao) {

    suspend fun getLocations(query: String): List<Location>? {
        var response: List<Location>? = null
        try {
            response = locationApiService.getLocations(cityName = query, appId = NetworkConstant.API_KEY)
        } catch (e: Exception) {
        }
        return response
    }

    suspend fun insertLocation(location: Location) = locationDao.insertLocation(location)
}
