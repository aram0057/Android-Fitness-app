package com.example.fit5046_project

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fit5046_project.ui.theme.FIT5046_projectTheme

@Composable
fun ExerciseTracker(exerciseViewModel: ExerciseViewModel) {
    val exercises by exerciseViewModel.allExercises.observeAsState(emptyList())
    val selectedExercise = remember { mutableStateOf<Exercise?>(null) }
    val insertDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = { insertDialog.value = true },
               modifier = Modifier.fillMaxWidth()) {
            Text("Add Exercise")
        }

        LazyColumn {
            itemsIndexed(exercises) { index, exercise ->
                ExerciseItem(
                    exercise = exercise,
                    onEdit = { selectedExercise.value = exercise },
                    onDelete = { exerciseViewModel.deleteExercise(exercise) }
                )
                Divider(color = Color.Gray, thickness = 5.dp)
            }
        }
    }

    if (insertDialog.value) {
        InsertExerciseDialog(
            onDismiss = { insertDialog.value = false },
            onSave = { name, minute ->
                exerciseViewModel.insertExercise(Exercise(name = name, minute = minute))
            }
        )
    }

    selectedExercise.value?.let { exercise ->
        EditExerciseDialog(
            exercise = exercise,
            onDismiss = { selectedExercise.value = null },
            onSave = { updatedExercise ->
                exerciseViewModel.updateExercise(updatedExercise)
                selectedExercise.value = null
            }
        )
    }
}

@Composable
fun InsertExerciseDialog(
    onDismiss: () -> Unit,
    onSave: (String, Int) -> Unit // Updated onSave function to accept both name and calorie
) {
    var name by remember { mutableStateOf("") }
    var minute by remember { mutableIntStateOf(0) }
    var isInputValid by remember { mutableStateOf(true) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Exercise") },
        confirmButton = {
            Button(
                onClick = {
                    if (validateExerciseInput(name, minute)) {
                        onSave(name, minute) // Convert calorie String to Int
                        onDismiss()
                    } else {
                        isInputValid = false
                    }
                }
            ){
                Text("Save")
            }

        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("Cancel") }
        },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Activity name") } // Label for food name input
                )
                Spacer(modifier = Modifier.height(8.dp)) // Add space between fields
                TextField(
                    value = minute.toString(),
                    onValueChange = { minute = it.toIntOrNull() ?: 0 },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Calories") } // Label for calorie input
                )
                if (!isInputValid) {
                    Text(
                        text = "Invalid input. Please check your input and try again.",
                        color = Color.Red,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                }

    }
    )
}

private fun validateExerciseInput(name: String, calorie: Int): Boolean {

    val namePattern = Regex("^[a-zA-Z]+$")
    val caloriePattern = Regex("^(?:[1-9]\\d{0,3}|[1-9]\\d{0,3})$")  // Range: 1-9999, leading zeros not allowed

    if (!namePattern.matches(name)) {
        return false
    }
    if (!caloriePattern.matches(calorie.toString())) {
        return false
    }
    return true
}








@Composable
fun EditExerciseDialog(exercise: Exercise, onDismiss: () -> Unit, onSave: (Exercise) -> Unit) {
    var editedExercise by remember { mutableStateOf(exercise) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Exercise") },
        confirmButton = {
            Button(
                onClick = {
                    onSave(editedExercise)
                    onDismiss()
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        text = {
            Column {
                TextField(
                    value = editedExercise.name,
                    onValueChange = { editedExercise = editedExercise.copy(name = it) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Excercise") } // Label for food name input
                )
                Spacer(modifier = Modifier.height(8.dp)) // Add space between fields
                TextField(
                    value = editedExercise.minute.toString(),
                    onValueChange = { editedExercise = editedExercise.copy(minute = it.toIntOrNull() ?: 0) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Calories") } // Label for calorie input
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ExercisePreview() {
    val context = LocalContext.current
    val exerciseViewModel = ExerciseViewModel(context.applicationContext as Application)
    FIT5046_projectTheme {
        ExerciseTracker(exerciseViewModel)
    }
}




