package com.example.weather.screens.addLocation

import androidx.lifecycle.ViewModel
import com.example.weather.room.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddLocationViewModel @Inject constructor(private val repository: AddLocationRepository) : ViewModel() {

    suspend fun searchLocation(queryString: String) = repository.getLocations(queryString)

    suspend fun insertLocation(location: Location) = repository.insertLocation(location)

}

