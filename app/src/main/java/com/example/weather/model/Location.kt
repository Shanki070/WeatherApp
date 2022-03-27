package com.example.weather.model

data class Location(
    val name: String,
    val lat: Double,
    val lon: Double,
    val state: String,
    val country: String,
)