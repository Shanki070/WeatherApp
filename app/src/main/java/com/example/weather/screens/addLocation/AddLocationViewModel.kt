package com.example.weather.screens.addLocation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.room.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class AddLocationViewModel @Inject constructor(private val repository: AddLocationRepository) : ViewModel() {


    var location: Location? = null

    suspend fun searchLocation(queryString: String) = viewModelScope.async(Dispatchers.IO) {
        repository.getLocations(queryString)
    }.await()

    suspend fun insertLocation(location: Location) = repository.insertLocation(location)


    override fun onCleared() {
        super.onCleared()
    }
}

