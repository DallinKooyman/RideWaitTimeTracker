package com.dallinkooyman.disneyridetimecomparison.data.ride

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RideDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ride: RideEntity)

    @Update
    suspend fun update(ride: RideEntity)

    @Delete
    suspend fun delete(ride: RideEntity)

    @Query("Select * from rides where id = :id")
    fun getRide(id: Int): Flow<RideEntity>

    @Query("Select * from rides where name = :name")
    fun getRideByName(name: String): Flow<RideEntity?>

    @Query("Select * from rides")
    fun getAllRides(): Flow<List<RideEntity>>

}