package com.example.weather.model

import com.squareup.moshi.Json

data class WeatherForecast(
    val lat: Double,
    val lon: Double,
    val state: String,
    val country: String,

    @Json(name = "dt") val date: Long,
    @Json(name = "main") val temperature: Temperature,
    val weather: List<Weather>
)