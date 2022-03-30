package com.example.weather.screens.locationList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.weather.room.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationListViewModel @Inject internal constructor(locationListRepository: LocationListRepository) : ViewModel() {

    val dBLocationList: LiveData<List<Location>> = locationListRepository.getAllLocations()
}
