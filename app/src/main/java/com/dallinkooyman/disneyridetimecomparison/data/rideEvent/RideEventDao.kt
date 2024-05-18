package com.dallinkooyman.disneyridetimecomparison.data.rideEvent

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RideEventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rideEventEntity: RideEventEntity)

    @Update
    suspend fun update(rideEventEntity: RideEventEntity)

    @Delete
    suspend fun delete(rideEventEntity: RideEventEntity)

    @Query("Select * from rideEvents order by enteredLineTime desc")
    fun getAllRideEvents(): Flow<List<RideEventEntity>>

    @Query("Select * from rideEvents where id = :id")
    fun getRideEvent(id: Int): Flow<RideEventEntity>
}