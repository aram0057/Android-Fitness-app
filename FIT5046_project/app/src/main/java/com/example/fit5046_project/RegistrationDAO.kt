package com.example.fit5046_project

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RegistrationDAO {
    @Query("SELECT * FROM Registration")
    fun getAllRegis(): Flow<List<Registration>>

    @Insert
    suspend fun insertRegis(registration: Registration)
    @Update
    suspend fun updateRegis(registration: Registration)
    @Delete
    suspend fun deleteRegis(registration: Registration)

    @Query("SELECT COUNT(*) FROM Registration WHERE email = :email")
    suspend fun isEmailRegistered(email: String): Int

    @Query("SELECT * FROM registration WHERE email = :email")
    suspend fun getUserByEmail(email: String): Registration?

    @Query("SELECT preferredName FROM Registration where email  = :email LIMIT 1")
    fun getPreferredName(email: String): Flow<String?>

    @Query("SELECT password FROM Registration where email  = :email LIMIT 1")
    fun getPassword(email: String): Flow<String?>

    @Query("SELECT confirmPassword FROM Registration where email  = :email LIMIT 1")
    fun getConfirmedPassword(email: String): Flow<String?>

    @Query("SELECT dob FROM Registration where email  = :email LIMIT 1")
    fun getDob(email: String): Flow<String?>

    @Query("SELECT gender FROM Registration where email  = :email LIMIT 1")
    fun getGender(email: String): Flow<String?>

    @Query("SELECT age FROM Registration where email  = :email LIMIT 1")
    fun getAge(email: String): Flow<String?>

    @Query("SELECT height FROM Registration where email  = :email LIMIT 1")
    fun getHeight(email: String): Flow<Int?>

    @Query("SELECT weight FROM Registration where email  = :email LIMIT 1")
    fun getWeight(email: String): Flow<Int?>

    @Query("SELECT desiredWeight FROM Registration where email  = :email LIMIT 1")
    fun getDesiredWeight(email: String): Flow<Int?>
}

