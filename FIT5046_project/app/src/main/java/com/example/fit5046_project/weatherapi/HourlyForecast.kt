package com.example.fit5046_project.weatherapi

data class HourlyForecast(
    val time: List<String> = ArrayList(),
    val temperature_2m: List<Double> = ArrayList(),
    val rain: List<Double> = ArrayList()
)