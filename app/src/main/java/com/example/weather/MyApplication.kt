package com.example.weather

import android.app.Application
import com.example.weather.background.scheduleJobToFetchWeatherDetails
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        ////////////////////////////
        //start fetching weather details once in a day
        scheduleJobToFetchWeatherDetails(1)
        ////////////////////////////
    }
}