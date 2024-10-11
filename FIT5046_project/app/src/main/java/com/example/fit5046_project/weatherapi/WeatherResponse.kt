package com.example.fit5046_project.weatherapi

data class WeatherResponse (
    val hourly: HourlyForecast = HourlyForecast()
)