package com.example.weather.model

import com.squareup.moshi.Json

data class Forecast(
    val lat: Double,
    val lon: Double,
    val state: String,
    val country: String,

    @Json(name = "dt") val dateInLong: Long,
    @Json(name = "main") val temperature: Temperature,
    val weather: List<Weather>
)