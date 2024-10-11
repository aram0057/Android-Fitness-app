package com.example.fit5046_project.weatherapi

class ForecastRepository {
    private val weatherService = RetrofitObject.retrofitService
    private val LATITUDE = -37.814
    private val LONGITUDE = 144.9633
    private val HOURLY = "temperature_2m,rain"
    suspend fun getResponse(): WeatherResponse {
        return weatherService.getWeather(
            LATITUDE,
            LONGITUDE,
            HOURLY
        )
    }
}