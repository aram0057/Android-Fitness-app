package com.example.fit5046_project

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Registration(
    @PrimaryKey
    val email: String,
    val password: String,
    val confirmPassword: String,
    val dob: String,
    val preferredName: String,
    val gender: String,
    val age: String,
    val height: Int,
    val weight: Int,
    val desiredWeight: Int
)