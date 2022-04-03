package com.example.weather.background

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context


fun Context.scheduleJobToFetchWeatherDetails(minimumLatency: Long) {
    val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
    val jobInfo = JobInfo.Builder(
        BackGroundConstant.JOB_ID_WEATHER_SERVICE,
        ComponentName(this, WeatherJobService::class.java)
    )
    val job = jobInfo.setRequiresCharging(false)
        .setMinimumLatency(minimumLatency)
        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
        .setOverrideDeadline((3*60*1000).toLong()).build()
    jobScheduler.schedule(job)
}