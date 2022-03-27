package com.example.weather.network

import com.example.weather.model.ForecastResponse
import com.example.weather.room.Location
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApiService {
    @GET("/data/2.5/forecast")
    suspend fun getForecastForNext5Days(@Query("lat") lat: String,
                                        @Query("lon") lon: String,
                                        @Query("units") unit: String = "metric",  //to get temperature in Celsius
                                        @Query("appid") appId: String): ForecastResponse
}

interface LocationApiService {
    @GET("/geo/1.0/direct")
    suspend fun getLocations(@Query("q") cityName: String,
                             @Query("limit") limit: String = "5",
                             @Query("appid") appId: String): List<Location>
}