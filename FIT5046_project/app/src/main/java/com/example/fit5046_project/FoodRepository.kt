package com.example.fit5046_project

import android.app.Application
import kotlinx.coroutines.flow.Flow

class FoodRepository (application: Application) {
    private var foodDao: FoodDAO = FoodDatabase.getDatabase(application).foodDAO()
    val allFoods: Flow<List<Food>> = foodDao.getAllFoods()
    suspend fun insert(food: Food) {
        foodDao.insertFood(food)
    }
    suspend fun delete(food: Food) {
        foodDao.deleteFood(food)
    }
    suspend fun update(food: Food) {
        foodDao.updateFood(food)
    }
}
