package com.example.fit5046_project

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fit5046_project.ui.theme.FIT5046_projectTheme
import java.time.Instant
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calendar() {
    val calendar = Calendar.getInstance()
    calendar.set(2024, 0, 1) // month (0) is January
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli())
    var selectedDate by remember {
        mutableStateOf(calendar.timeInMillis) }

    Column(modifier = Modifier.padding(16.dp),
           horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "CALORIE TRACKER",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(40.dp))
        DatePicker(
            state = datePickerState
        )

        Button(
            onClick = {
                selectedDate = datePickerState.selectedDateMillis ?: selectedDate
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "OK")
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun CalendarPagePreview() {
    FIT5046_projectTheme {
        Calendar()
    }
}