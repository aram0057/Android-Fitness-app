package com.example.fit5046_project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FoodItem(food: Food, onEdit: () -> Unit, onDelete: () -> Unit) {
    Row(
    modifier = Modifier .fillMaxWidth() .padding(8.dp),
    horizontalArrangement = Arrangement.SpaceBetween ){
    Text(
        text = food.name,
        modifier = Modifier.weight(1f)
    )
        Text(
            text = food.calorie.toString(),
            modifier = Modifier.weight(1f)
        )
    IconButton(onClick = onEdit) {
        Icon(Icons.Default.Edit, contentDescription = "Edit")
    }
    IconButton(onClick = onDelete) {
        Icon(Icons.Default.Delete, contentDescription = "Delete")
    }
    }
}