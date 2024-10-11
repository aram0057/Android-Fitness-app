package com.example.fit5046_project

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.TextStyle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fit5046_project.weatherapi.ForecastRepository
import com.example.fit5046_project.weatherapi.WeatherResponse
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

data class DailyWeatherData(
    val dayOfWeek: DayOfWeek,
    val date: LocalDate,
    val minTemp: Double,
    val maxTemp: Double,
    val totalRain: Double
)

class WorkoutRecommendationViewModel : ViewModel() {
    private val repository = ForecastRepository()

    val groupedData: MutableState<List<DailyWeatherData>> = mutableStateOf(emptyList())

    fun getResponse() {
        viewModelScope.launch {
            try {
                val responseReturned = repository.getResponse()

                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
                val dateTimes = responseReturned.hourly.time.map { LocalDateTime.parse(it, formatter) }
                val temperatures = responseReturned.hourly.temperature_2m
                val rain = responseReturned.hourly.rain

                // Create a list of triples (date, temperature, rain)
                val triplesWithDate = dateTimes.mapIndexed { index, dateTime ->
                    Triple(dateTime.toLocalDate(), temperatures[index], rain[index])
                }

                // Group the triples by date
                val groupedTriples = triplesWithDate.groupBy { it.first }

                // Transform the grouped triples into a list of DailyWeatherData objects
                val dailyWeatherData = groupedTriples.map { (date, triples) ->
                    // For each group (date and associated triples), create a DailyWeatherData object
                    DailyWeatherData(
                        dayOfWeek = date.dayOfWeek,
                        date = date,
                        minTemp = triples.minOf { it.second },
                        maxTemp = triples.maxOf { it.second },
                        totalRain = triples.sumOf { it.third }
                    )
                }

                groupedData.value = dailyWeatherData
            } catch (e: Exception) {
                Log.i("Error ", "Response failed")
            }
        }
    }
}