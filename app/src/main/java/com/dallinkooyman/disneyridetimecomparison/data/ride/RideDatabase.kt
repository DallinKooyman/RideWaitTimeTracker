package com.dallinkooyman.disneyridetimecomparison.data.ride

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RideEntity::class], version = 1, exportSchema = false)
abstract class RideDatabase : RoomDatabase() {

    abstract fun rideDao(): RideDao

    companion object {
        @Volatile
        private var Instance: RideDatabase? = null

        fun getDatabase(context: Context): RideDatabase {
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context, RideDatabase::class.java, "ride_database")
                    .fallbackToDestructiveMigration()
                    .createFromAsset("ride_database.db")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}