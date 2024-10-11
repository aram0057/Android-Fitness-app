package com.example.fit5046_project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel(private val repository: RegistrationRepository) : ViewModel() {
    private val _emailRegistered = MutableStateFlow(false)
    val emailRegistered: StateFlow<Boolean> = _emailRegistered

    private val _passwordCorrect = MutableStateFlow(false)
    val passwordCorrect: StateFlow<Boolean> = _passwordCorrect

    private val _preferredName = MutableStateFlow<String?>(null)
    val preferredName: StateFlow<String?> = _preferredName

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> = _userEmail


    // check if the email is registered
    suspend fun isEmailRegistered(email: String): Boolean {
        val isRegistered = repository.isEmailRegistered(email)
        _emailRegistered.value = isRegistered
        return isRegistered
    }

    // check if the password is correct
    suspend fun isPasswordCorrect(email: String, password: String): Boolean {
        val isCorrect = repository.isPasswordCorrect(email, password)
        _passwordCorrect.value = isCorrect
        return isCorrect
    }

    fun setUserEmail(email: String) {
        _userEmail.value = email
    }
}