package com.example.weather.util

import android.text.format.DateFormat
import java.util.*


object DateUtil {

    fun getTodayDateInLong() = Calendar.getInstance().let {
        it.time = Date()
        it.set(Calendar.HOUR_OF_DAY, 0)
        it.set(Calendar.MINUTE, 0)
        it.set(Calendar.SECOND, 0)
        it.set(Calendar.MILLISECOND, 0)
        it.timeInMillis / 1000
    }

    fun getDateWithoutTime(timestamp: Long): String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = timestamp * 1000L
        val date = DateFormat.format("dd MMM",calendar).toString()
        return date
    }

    fun getTimeWithoutDate(timestamp: Long): String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = timestamp * 1000L
        val date = DateFormat.format("hh aaa",calendar).toString()
        return date
    }
}