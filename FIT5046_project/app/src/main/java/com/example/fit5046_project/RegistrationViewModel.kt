package com.example.fit5046_project

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrationViewModel (application: Application) : AndroidViewModel(application) {
    private val cRepository: RegistrationRepository
    val userEmail: String = fetchUserEmail()

    init{
        cRepository = RegistrationRepository(application)
        //userEmail = fetchUserEmail()
    }
    val allRegistration: LiveData<List<Registration>> = cRepository.allRegistrations.asLiveData()
    fun insertRegis(registration: Registration) = viewModelScope.launch(Dispatchers.IO) {
        cRepository.insert(registration)
    }
    fun updateRegis(registration: Registration) = viewModelScope.launch(Dispatchers.IO) {
        cRepository.update(registration)
    }
    fun deleteRegis(registration: Registration) = viewModelScope.launch(Dispatchers.IO) {
        cRepository.delete(registration)
    }

    fun getPreferredName(userEmail: String): Flow<String?> {
        return cRepository.getPreferredName(email = userEmail)
    }
    fun getPassword(userEmail: String): Flow<String?> {
        return cRepository.getPassword(email = userEmail)
    }
    fun getConfirmedPassword(userEmail: String): Flow<String?> {
        return cRepository.getConfirmedPassword(email = userEmail)
    }
    fun getDob(userEmail: String): Flow<String?> {
        return cRepository.getDob(email = userEmail)
    }
    fun getGender(userEmail: String): Flow<String?> {
        return cRepository.getGender(email = userEmail)
    }
    fun getAge(userEmail: String): Flow<String?> {
        return cRepository.getAge(email = userEmail)
    }
    fun getHeight(userEmail: String): Flow<Int?> {
        return cRepository.getHeight(email = userEmail)
    }
    fun getWeight(userEmail: String): Flow<Int?> {
        return cRepository.getWeight(email = userEmail)
    }
    fun getDesiredWeight(userEmail: String): Flow<Int?> {
        return cRepository.getDesiredWeight(email = userEmail)
    }

    suspend fun getUserByEmail(email: String): Registration? {
        return withContext(Dispatchers.IO) {
            cRepository.getUserByEmail(email)
        }
    }

    private fun fetchUserEmail(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        Log.d(TAG, "fetchUserEmail: ")
        return currentUser?.email ?: ""
    }
}
