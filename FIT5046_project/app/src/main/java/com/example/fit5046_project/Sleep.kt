package com.example.fit5046_project

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "Sleep")
data class Sleep(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val startTime: Long,
    val endTime: Long,
    val quality: Int
)