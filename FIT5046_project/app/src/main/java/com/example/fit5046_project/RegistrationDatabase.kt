package com.example.fit5046_project

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Registration::class], version = 1, exportSchema = false)
abstract class RegistrationDatabase : RoomDatabase() {
    abstract fun registrationDAO(): RegistrationDAO
    companion object {
        @Volatile
        private var INSTANCE: RegistrationDatabase? = null

        fun getDatabase(context: Context): RegistrationDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RegistrationDatabase::class.java, "registration_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}