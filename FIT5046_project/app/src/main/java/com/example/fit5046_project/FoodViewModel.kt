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

class FoodViewModel(application: Application) : AndroidViewModel(application) {
    private val cRepository: FoodRepository
    init{
        cRepository = FoodRepository(application) }

    val allFoods: LiveData<List<Food>> = cRepository.allFoods.asLiveData()
    fun insertFood(food: Food) = viewModelScope.launch(Dispatchers.IO) {
        cRepository.insert(food)
    }
    fun updateFood(food: Food) = viewModelScope.launch(Dispatchers.IO) {
        cRepository.update(food)
    }
    fun deleteFood(food: Food) = viewModelScope.launch(Dispatchers.IO) {
        cRepository.delete(food)
    }
    fun getTotalCalories(): Flow<Int> {
        return cRepository.allFoods.map { foods ->
            var totalCalories = 0
            for (food in foods) {
                totalCalories += food.calorie
            }
            totalCalories
        }
    }


}