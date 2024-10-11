package com.example.fit5046_project

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fit5046_project.ui.theme.FIT5046_projectTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.ui.text.style.TextAlign
import java.time.LocalDate


@Composable
fun WorkoutRecommendation(navController: NavController, viewModel: WorkoutRecommendationViewModel) {
    val groupedData by viewModel.groupedData
    val today = LocalDate.now()

    viewModel.getResponse()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(groupedData.size) { index ->
            val dailyData = groupedData[index]
            WeatherCard(
                dayOfWeek = if (dailyData.date.isEqual(today)) "TODAY" else dailyData.dayOfWeek.toString(),
                date = dailyData.date,
                temperatureRange = "${dailyData.minTemp}°C - ${dailyData.maxTemp}°C",
                totalRainfall = "${dailyData.totalRain} mm",
                minTemp = dailyData.minTemp,
                maxTemp = dailyData.maxTemp,
                totalRain = dailyData.totalRain
            )
        }
    }
}

@Composable
fun WeatherCard(
    dayOfWeek: String,
    date: LocalDate,
    temperatureRange: String,
    totalRainfall: String,
    minTemp: Double,
    maxTemp: Double,
    totalRain: Double
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(4.dp)
        ) {
            Text(
                text = dayOfWeek,
            )
            Text(
                text = date.toString(),
            )
            Text(
                text = "Temperature Range: $temperatureRange",
            )
            Text(
                text = "Total Rainfall: $totalRainfall",
            )

            if (minTemp >= 8.0 && totalRain <= 2.0) {
                Card(
                    colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                    )
                ) {
                    Text(
                        text = "Good weather for an outdoor workout!",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(4.dp)
                    )
                }

            } else {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                    )
                ) {
                    Text(
                        text = "Better workout indoors today.",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }

        }
    }
}

// Function to check if a date is today

@Preview(showBackground = true)
@Composable
fun WorkoutRecommendationPreview() {
    FIT5046_projectTheme {
        val navController = rememberNavController()
        val workoutViewModel = viewModel<WorkoutRecommendationViewModel>()
        WorkoutRecommendation(navController = navController, workoutViewModel)
    }
}