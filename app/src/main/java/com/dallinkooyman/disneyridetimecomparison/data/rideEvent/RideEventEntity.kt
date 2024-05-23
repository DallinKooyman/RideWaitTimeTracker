package com.dallinkooyman.disneyridetimecomparison.data.rideEvent

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dallinkooyman.disneyridetimecomparison.model.RideEvent

@Entity(tableName = "rideEvents")
data class RideEventEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val rideId: String,
    val rideName: String,
    val enteredLineTime: Long,
    val gotOnRideTime: Long,
    val apiPostedTimeForEvent: Int,
    val apiAndPostedAreSame: Int,
    val ridePostedTime: Int?,
    val timeWaited: Int,
    val hasInteractable: Boolean,
    val timeUntilInteractable: Int,
    val apiDifferenceInMinutes: Int,
    val ridePostedDifferenceInMinutes: Int,
    val apiToPostedDifferenceInMinutes: Int,
    val apiToWaitDifferenceInPercent: Double,
    val rideToWaitDifferenceInPercent: Double,
    val apiToRideDifferenceInPercent: Double
)

fun RideEventEntity.toRideEventModel(): RideEvent {
    return RideEvent(
        id, rideId, rideName, enteredLineTime, gotOnRideTime, apiPostedTimeForEvent, ridePostedTime,
        timeWaited, hasInteractable, timeUntilInteractable, apiAndPostedAreSame == 1
    )
}