package com.example.fit5046_project.weatherapi

import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {
    @GET("forecast")
    suspend fun getWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("hourly") hourly: String
    ): WeatherResponse
}