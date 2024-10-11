package com.example.fit5046_project

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Food(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    val name: String,
    val calorie: Int
)