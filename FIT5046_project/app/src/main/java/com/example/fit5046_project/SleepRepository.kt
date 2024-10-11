package com.example.fit5046_project

import kotlinx.coroutines.flow.Flow

class SleepRepository(private val sleepDao: SleepDAO) {

    val allSleepData: Flow<List<Sleep>> = sleepDao.getSleep()

    suspend fun insert(sleep: Sleep) {
        sleepDao.insertSleep(sleep)
    }

    suspend fun update(sleep: Sleep) {
        sleepDao.updateSleep(sleep)
    }

    suspend fun delete(sleep: Sleep) {
        sleepDao.deleteSleep(sleep)
    }
}
