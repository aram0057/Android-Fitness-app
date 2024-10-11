package com.example.fit5046_project

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fit5046_project.ui.theme.FIT5046_projectTheme
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlin.math.absoluteValue


@Composable
fun Track(navController: NavHostController) {
    val context = LocalContext.current
    val foodViewModel = FoodViewModel(context.applicationContext as Application)
    val exerciseViewModel = ExerciseViewModel(context.applicationContext as Application)
    var caloriesConsumed by remember { mutableStateOf(0) }
    var showBarChart by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        foodViewModel.getTotalCalories().collect {
            caloriesConsumed = it
        }
    }
    var caloriesBurned by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        exerciseViewModel.getTotalWorkoutCalories().collect {
            caloriesBurned = it
        }
    }



    Column(modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceTint
        ) {
            androidx.compose.material.Text(
                text = " Calorie Tracker",
                modifier = Modifier.padding(16.dp),
                style = androidx.compose.material.MaterialTheme.typography.h6,
                color = Color.White
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally)
                .background(Color.LightGray)
        ) {
            Row {
                Spacer(modifier = Modifier.width(16.dp)) // Adjusted width for spacing
                Column(modifier = Modifier.weight(1f)) {
                    Spacer(modifier = Modifier.height(16.dp))
                    SectionHeading("Calories Intake")
                    Spacer(modifier = Modifier.height(16.dp))
                    FoodTracker(foodViewModel)
                }
                Spacer(modifier = Modifier.width(16.dp)) // Adjusted width for spacing
            }
        }
        Spacer(modifier = Modifier.height(16.dp)) // Adjusted height for spacing
        Box(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally)
                .background(Color.LightGray)
        ) {
            Row {
                Spacer(modifier = Modifier.width(16.dp)) // Adjusted width for spacing
                Column(modifier = Modifier.weight(1f)) {
                    Spacer(modifier = Modifier.height(16.dp))
                    SectionHeading("Calories burned")
                    Spacer(modifier = Modifier.height(16.dp))
                    ExerciseTracker(exerciseViewModel)
                }
                Spacer(modifier = Modifier.width(16.dp)) // Adjusted width for spacing
            }
        }

        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Calories consumed = $caloriesConsumed", color = Color.Red)
                Text(text = "Calories Burned = $caloriesBurned", color = Color.Blue)
            }
        }

        if (showBarChart) {
            fitnessMessage(
                caloriesConsumed = caloriesConsumed,
                caloriesBurned = caloriesBurned,
                currentWeight = 45.00,
                desiredWeight = 54.00
            )
            BarChartScreen(caloriesConsumed, caloriesBurned)
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                Button(
                    onClick = {
                        showBarChart = true
                    },
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                ) {
                    Text(text = "Show Calories Chart")
                }
            }
        }
    }
}

@Composable
fun ButtonWithVisibility() {
    var showButton by remember { mutableStateOf(true) }
    var showBarChart by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        if (showButton) {
            Button(
                onClick = {
                    showBarChart = true
                    showButton = false
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp) // Add padding to all sides
                    .fillMaxWidth()
            ) {
                Text(text = "Show Calories Chart")
            }
        }

        if (showBarChart) {

            Text(text = "Calories Chart is shown!")
        }
    }
}


@Composable
fun fitnessMessage(
    caloriesConsumed: Int,
    caloriesBurned: Int,
    currentWeight: Double,
    desiredWeight: Double
) {
   // val bmr = calculateBMR(currentWeight, isMale = true) // Assuming male, change to false for female

    // Calculate net caloric surplus/deficit
    val netCalories = caloriesConsumed - caloriesBurned

    // Calories per kilogram of body weight
    val caloriesPerKg = 7700 // Approximate calories per kilogram of body weight for weight change

    // Calculate weight change over 30 days
    val weightChange = (netCalories * 30) / caloriesPerKg

    // Calculate new weight after 30 days
    val newWeight = currentWeight + weightChange

    // Calculate weight difference from desired weight
    val weightDifference = newWeight - desiredWeight

    // Determine fitness message based on weight change
    val fitnessMessage = when {
        weightDifference > 0 -> "Based on your net calories, you would gain ${weightDifference.toInt()} kilograms in 30 days. Your new weight would be ${newWeight.toInt()} kilograms."
        weightDifference < 0 -> "Based on your net calories, you would lose ${weightDifference.toInt().absoluteValue} kilograms in 30 days. Your new weight would be ${newWeight.toInt()} kilograms."
        else -> "Based on your net calories, your weight would remain unchanged in 30 days."
    }

    // Display fitness message
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = fitnessMessage)
    }
}


fun calculateBMR(currentWeight: Double, isMale: Boolean): Double {
    return if (isMale) {
        // Harris-Benedict equation for men
        88.362 + (13.397 * currentWeight) // This is a simplified version without height and age for brevity
    } else {
        447.593 + (9.247 * currentWeight)
    }
}


@Composable
fun SectionHeading(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun BarChartScreen(caloriesConsumed: Int, caloriesBurned: Int) {
    val barEntries = listOf(
        BarEntry(0f, caloriesConsumed.toFloat()),
        BarEntry(1f, caloriesBurned.toFloat())
    )
    val barDataSet = BarDataSet(barEntries, "Calories")
    barDataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()
    val barData = BarData(barDataSet)
    barData.barWidth =0.5f
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            BarChart(context).apply {
                data = barData
                description.isEnabled = false
                setFitBars(true)
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.valueFormatter =
                    IndexAxisValueFormatter(listOf("Calories Consumed", "Calories Burned"))
                animateY(3000)
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun TrackPreview() {
    FIT5046_projectTheme {
        val navController = rememberNavController()
        Track(navController = navController)
    }
}
