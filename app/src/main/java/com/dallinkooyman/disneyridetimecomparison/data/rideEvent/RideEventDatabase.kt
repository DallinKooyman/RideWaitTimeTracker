package com.dallinkooyman.disneyridetimecomparison.data.rideEvent

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RideEventEntity::class], version = 2, exportSchema = false)
abstract class RideEventDatabase : RoomDatabase() {

    abstract fun rideEventDao(): RideEventDao


    companion object{
        @Volatile
        private var Instance: RideEventDatabase? = null

        fun getDatabase(context: Context): RideEventDatabase {
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context, RideEventDatabase::class.java, "ride_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}