package com.example.fit5046_project

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

enum class MealType {
    BREAKFAST,
    LUNCH,
    DINNER,
    SNACK
}

enum class FoodType {
    VEGAN,
    NON_VEG,
    VEG
}

data class FoodRecommendation(val meal: MealType, val foodType: FoodType, val recommendation: String)

@Composable
fun DropdownListWithRecommendation() {
    var showMenu by remember { mutableStateOf(false) }
    var showMenu1 by remember { mutableStateOf(false) }
    var selectedMeal by remember { mutableStateOf<MealType?>(null) }
    var selectedFoodType by remember { mutableStateOf<FoodType?>(null) }

    val recommendations = remember {
        mutableStateListOf(  //
            FoodRecommendation(MealType.BREAKFAST, FoodType.VEGAN, "Oatmeal"),
            FoodRecommendation(MealType.BREAKFAST, FoodType.NON_VEG, "Eggs and Bacon"),
            FoodRecommendation(MealType.BREAKFAST, FoodType.VEG, "Toast with Avocado"),
            FoodRecommendation(MealType.LUNCH, FoodType.VEGAN, "Quinoa Salad"),
            FoodRecommendation(MealType.LUNCH, FoodType.NON_VEG, "Chicken Sandwich"),
            FoodRecommendation(MealType.LUNCH, FoodType.VEG, "Caprese Salad"),
            FoodRecommendation(MealType.DINNER, FoodType.VEGAN, "Vegetable Stir Fry"),
            FoodRecommendation(MealType.DINNER, FoodType.NON_VEG, "Steak and Potatoes"),
            FoodRecommendation(MealType.DINNER, FoodType.VEG, "Pasta Primavera"),
            FoodRecommendation(MealType.SNACK, FoodType.VEGAN, "Fruit Salad"),
            FoodRecommendation(MealType.SNACK, FoodType.NON_VEG, "Cheese and Crackers"),
            FoodRecommendation(MealType.SNACK, FoodType.VEG, "Carrot Sticks")
        )
    }


    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceTint
    ) {
        Text(
            text = "Food Recommendations",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
    }


        Column(

            modifier = Modifier.padding(vertical = 50.dp, horizontal = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { showMenu = true }) {
                Text(text = selectedMeal?.name ?: "Select a Meal")
            }

            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                MealType.values().forEach { meal ->
                    DropdownMenuItem(
                        onClick = {
                            selectedMeal = meal
                            showMenu = false
                            selectedFoodType = null // Reset food type when meal changes

                        }
                    ) {
                        Text(text = meal.name)

                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            selectedMeal?.let { meal ->
                Button(onClick = { showMenu1 = true }) {
                    Text(text = selectedFoodType?.name ?: "Food preference")
                }

                DropdownMenu(
                    expanded = showMenu1,
                    onDismissRequest = {
                        showMenu1 = false
                    }
                ) {
                    FoodType.values().forEach { foodType ->
                        DropdownMenuItem(
                            onClick = {
                                selectedFoodType = foodType
                                showMenu1 = false
                            }
                        ) {
                            Text(text = foodType.name)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            selectedMeal?.let { meal ->
                selectedFoodType?.let { foodType ->
                    val recommendation = recommendations.find {
                        it.meal == meal && it.foodType == foodType
                    }?.recommendation ?: "No recommendation found."

                    Text("Recommendation for ${meal.name} (${foodType.name}): $recommendation")
                }
            }
        }

    }




@Preview
@Composable
fun App() {
    DropdownListWithRecommendation()
}




