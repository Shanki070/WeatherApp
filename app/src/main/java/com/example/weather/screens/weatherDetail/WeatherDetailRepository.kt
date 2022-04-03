package com.example.weather.screens.weatherDetail

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.weather.network.ForecastApiService
import com.example.weather.network.NetworkConstant
import com.example.weather.room.Forecast
import com.example.weather.room.ForecastDao
import com.example.weather.room.LocationDao
import com.example.weather.util.DateUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherDetailRepository @Inject constructor(private val forecastApiService: ForecastApiService,
                                                  private val locationDao: LocationDao,
                                                  private val forecastDao: ForecastDao) {

    private suspend fun checkForUpdate(lat: Double, lon: Double) {
        try {
            if (forecastDao.getListCount(lat, lon) <= 0 ||
                forecastDao.getForecastMaxDate(lat, lon) < DateUtil.getTodayDateInLong()) { //TODO shanki date, time-zone
                //no details or maximum date get older than current date
                //then update the list
                getForeCastFromServer(lat, lon)
            }
        } catch (e: Exception) {
            Log.d("API Error", "Error ${e.message}")
        }
    }


    suspend fun loadForeCast() = CoroutineScope(Dispatchers.IO).async {
        locationDao.getAllLocations()?.map { location ->
            async {
                getForeCastFromServer(location.lat, location.lon)
            }
        }?.awaitAll()
    }.await()

    private suspend fun getForeCastFromServer(lat: Double, lon: Double) {
        val response = forecastApiService.getForecastForNext5Days(lat, lon, appId = NetworkConstant.API_KEY)
        response.list.forEach { location ->
            location.weather.forEach { weather ->
                val forecast = Forecast(lat = lat, lon = lon,
                    date = location.date,
                    minTemperature = location.temperature.temp_min,
                    maxTemperature = location.temperature.temp_max,
                    weatherIcon = weather.icon,
                    description = weather.description
                )
                forecastDao.insertWeatherDetails(forecast)
            }
        }
    }

    suspend fun getForeCastFromDB(lat: Double, lon: Double): LiveData<List<Forecast>?> {
        checkForUpdate(lat, lon)
        return forecastDao.getForecast(lat, lon)
    }
}
