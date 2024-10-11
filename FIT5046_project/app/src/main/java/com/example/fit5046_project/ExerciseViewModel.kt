package com.example.fit5046_project

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ExerciseViewModel(application: Application) : AndroidViewModel(application) {
    private val cRepository: ExerciseRepository
    init{
        cRepository = ExerciseRepository(application) }

    val allExercises: LiveData<List<Exercise>> = cRepository.allExercises.asLiveData()
    fun insertExercise(exercise: Exercise) = viewModelScope.launch(Dispatchers.IO) {
        cRepository.insert(exercise)
    }
    fun updateExercise(exercise: Exercise) = viewModelScope.launch(Dispatchers.IO) {
        cRepository.update(exercise)
    }
    fun deleteExercise(exercise: Exercise) = viewModelScope.launch(Dispatchers.IO) {
        cRepository.delete(exercise)
    }
    fun getTotalWorkoutCalories(): Flow<Int> {
        return cRepository.allExercises.map { Excercise ->
            var totalCalories = 0
            for (exercise in Excercise) {
                totalCalories += exercise.minute
            }
            totalCalories
        }
    }

}