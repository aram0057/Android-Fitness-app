package com.example.fit5046_project

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class SleepViewModel(private val repository: SleepRepository) : ViewModel() {

    val allSleepData: LiveData<List<Sleep>> = repository.allSleepData.asLiveData()

    fun insert(sleep: Sleep) = viewModelScope.launch {
        repository.insert(sleep)
    }

    fun update(sleep: Sleep) = viewModelScope.launch {
        repository.update(sleep)
    }

    fun delete(sleep: Sleep) = viewModelScope.launch {
        repository.delete(sleep)
    }
}
