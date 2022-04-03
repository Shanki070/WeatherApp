package com.example.weather.model

import com.squareup.moshi.Json

data class WeatherForecast(
    @Json(name = "dt") val date: Long,
    @Json(name = "main") val temperature: Temperature,
    val weather: List<Weather>
)