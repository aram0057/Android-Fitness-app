package com.example.fit5046_project

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDAO {
    @Query("SELECT * FROM Exercise")
    fun getAllExercises(): Flow<List<Exercise>>
    @Insert
    suspend fun insertExercise(exercise: Exercise)
    @Update
    suspend fun updateExercise(exercise: Exercise)
    @Delete
    suspend fun deleteExercise(exercise: Exercise)
}