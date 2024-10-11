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
fun FoodTracker(foodViewModel: FoodViewModel) {
    val foods by foodViewModel.allFoods.observeAsState(emptyList())
    val selectedFood = remember { mutableStateOf<Food?>(null) }
    val insertDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = { insertDialog.value = true },
            modifier = Modifier.fillMaxWidth()) {
            Text("Add Food")
        }

        LazyColumn {
            itemsIndexed(foods) { index, food ->
                FoodItem(
                    food = food,
                    onEdit = { selectedFood.value = food },
                    onDelete = { foodViewModel.deleteFood(food) }
                )
                Divider(color = Color.Gray, thickness = 5.dp)
            }
        }
    }

    if (insertDialog.value) {
        InsertFoodDialog(
            onDismiss = { insertDialog.value = false },
            onSave = { name, calorie ->
                foodViewModel.insertFood(Food(name = name, calorie = calorie))
            }
        )
    }

    selectedFood.value?.let { food ->
        EditFoodDialog(
            food = food,
            onDismiss = { selectedFood.value = null },
            onSave = { updatedFood ->
                foodViewModel.updateFood(updatedFood)
                selectedFood.value = null
            }
        )
    }
}

@Composable
fun InsertFoodDialog(
    onDismiss: () -> Unit,
    onSave: (String, Int) -> Unit // Updated onSave function to accept both name and calorie
) {
    var foodName by remember { mutableStateOf("") }
    var calorie by remember { mutableIntStateOf(0) } // Calorie represented as a String
    var isInputValid by remember { mutableStateOf(true) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Food") },
        confirmButton = {
            Button(
                onClick = {
                    if (validateInput(foodName, calorie.toInt().toString())) {
                        onSave(foodName, calorie.toInt())
                        onDismiss()
                    } else {
                        isInputValid = false

                    }

                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("Cancel") }
        },
        text = {
            Column {
                TextField(
                    value = foodName,
                    onValueChange = { foodName = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Food Name") } // Label for food name input
                )
                Spacer(modifier = Modifier.height(8.dp)) // Add space between fields
                TextField(
                    value = calorie.toString(),
                    onValueChange = { calorie = it.toIntOrNull() ?: 0 },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Calorie") } // Label for calorie input
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
private fun validateInput(foodName: String, calorie: String): Boolean {

    val namePattern = Regex("^[a-zA-Z]+$")
    val caloriePattern = Regex("^(?:[1-9]\\d{0,3}|10000)$")



    if (!namePattern.matches(foodName)) {
        return false
    }
    if (calorie.isEmpty() || !caloriePattern.matches(calorie) || calorie.toInt() < 0 || calorie.toInt() > 10000) {
        return false
    }
    return true

}




@Composable
fun EditFoodDialog(food: Food, onDismiss: () -> Unit, onSave: (Food) -> Unit) {
    var editedFood by remember { mutableStateOf(food) }
    var isInputValid by remember { mutableStateOf(true) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Food") },
        confirmButton = {
            Button(
                onClick = {
                    onSave(editedFood)
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
                    value = editedFood.name,
                    onValueChange = { editedFood = editedFood.copy(name = it) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Food Name") } // Label for food name input
                )
                Spacer(modifier = Modifier.height(8.dp)) // Add space between fields
                TextField(
                    value = editedFood.calorie.toString(),
                    onValueChange = {
                        editedFood = editedFood.copy(calorie = it.toIntOrNull() ?: 0)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Calorie") } // Label for calorie input
                )
                if (!isInputValid) {
                    Text(
                        text = "Invalid input. Please check your input and try again.",
                        color = Color.Red,
                        modifier = Modifier.padding(8.dp)
                    )

                    //need to apply validation to the actual if condition
                }

            }

        }
    )
}


@Preview(showBackground = true)
@Composable
fun FoodPreview() {
    val context = LocalContext.current // Obtain a valid context
    val foodViewModel = FoodViewModel(context.applicationContext as Application)
    FIT5046_projectTheme {
        FoodTracker(foodViewModel)
    }
}





