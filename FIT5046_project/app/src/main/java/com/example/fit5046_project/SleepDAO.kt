package com.example.fit5046_project

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
@Dao
interface SleepDAO {
    @Query("SELECT * FROM Sleep")
    fun getSleep(): Flow<List<Sleep>>

    @Insert
    suspend fun insertSleep(sleep: Sleep)

    @Update
    suspend fun updateSleep(sleep: Sleep)

    @Delete
    suspend fun deleteSleep(sleep: Sleep)
}