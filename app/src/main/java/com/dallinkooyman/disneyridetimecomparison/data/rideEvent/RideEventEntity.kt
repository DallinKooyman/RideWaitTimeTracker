package com.dallinkooyman.disneyridetimecomparison.data.rideEvent

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rideEvents")
data class RideEventEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val rideId: String,
    val rideName: String,
    val enteredLineTime: Long,
    val gotOnRideTime: Long,
    val apiPostedTimeForEvent: Int,
    val timeWaited: Int,
    val hasInteractable: Boolean,
    val timeUntilInteractable: Int
)