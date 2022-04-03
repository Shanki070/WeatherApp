package com.example.weather.screens.weatherDetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class WeatherDetailViewModel @Inject internal constructor(private val repository: WeatherDetailRepository)
    : ViewModel() {

    suspend fun getForecast(lat: Double, lon: Double) = viewModelScope.async(Dispatchers.IO) {
        repository.getForeCastFromDB(lat, lon)
    }.await()
}
