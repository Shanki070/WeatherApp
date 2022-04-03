package com.example.weather.room

import androidx.room.Entity

@Entity(primaryKeys= [ "lat", "lon" ])
data class Location(
    val lat: Double,
    val lon: Double,
    val name: String,
    val state: String? = null,
    val country: String,
) {
    fun getLocationName(): String {
        val name = StringBuilder(name)
        state?.apply { name.append(", $this")  }
        name.append(", $country")
        return name.toString()
    }
}