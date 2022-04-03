package com.example.weather.background

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import com.example.weather.screens.weatherDetail.WeatherDetailRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class WeatherJobService : JobService() {

    var params: JobParameters ?= null

    @Inject lateinit var repository: WeatherDetailRepository

    override fun onStartJob(params: JobParameters?): Boolean {
        this.params = params

        CoroutineScope(Dispatchers.IO).launch {
            repository.loadForeCast()

            //by rescheduling the current job get cancelled automatically
            scheduleJobToFetchWeatherDetails(24*60*60*1000) //reschedule job after 24 hours
        }

        /*
         * True - if your service needs to process
         * the work (on a separate thread).
         * False - if there's no more work to be done for this job.
         */
        return true
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}