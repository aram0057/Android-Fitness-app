package com.example.fit5046_project

import android.app.Application
import kotlinx.coroutines.flow.Flow

class RegistrationRepository (application: Application) {
    private var registrationDao: RegistrationDAO =
        RegistrationDatabase.getDatabase(application).registrationDAO()
    val allRegistrations: Flow<List<Registration>> = registrationDao.getAllRegis()

    suspend fun insert(registration: Registration) {
        registrationDao.insertRegis(registration) }
    suspend fun delete(registration: Registration) {
        registrationDao.deleteRegis(registration)
    }
    suspend fun update(registration: Registration) {
        registrationDao.updateRegis(registration)
    }
    suspend fun isEmailRegistered(email: String): Boolean {
        return registrationDao.isEmailRegistered(email) > 0
    }
    suspend fun isPasswordCorrect(email: String, password: String): Boolean {
        // First, check if the email is registered
        val isEmailRegistered = isEmailRegistered(email)

        // If the email is not registered, return false
        if (!isEmailRegistered) {
            return false
        }
        // If the email is registered, retrieve the user record from the database
        val user = registrationDao.getUserByEmail(email)

        // Check if the provided password matches the stored password
        return user?.password == password
    }
    fun getPreferredName(email: String): Flow<String?> {
        return registrationDao.getPreferredName(email)
    }
    fun getPassword(email: String): Flow<String?> {
        return registrationDao.getPassword(email)
    }
    fun getConfirmedPassword(email: String): Flow<String?> {
        return registrationDao.getConfirmedPassword(email)
    }
    fun getDob(email: String): Flow<String?> {
        return registrationDao.getDob(email)
    }
    fun getGender(email: String): Flow<String?> {
        return registrationDao.getGender(email)
    }
    fun getAge(email: String): Flow<String?> {
        return registrationDao.getAge(email)
    }
    fun getHeight(email: String): Flow<Int?> {
        return registrationDao.getHeight(email)
    }
    fun getWeight(email: String): Flow<Int?> {
        return registrationDao.getWeight(email)
    }
    fun getDesiredWeight(email: String): Flow<Int?> {
        return registrationDao.getDesiredWeight(email)
    }
    suspend fun getUserByEmail(email: String): Registration? {
        return registrationDao.getUserByEmail(email)
    }
}

