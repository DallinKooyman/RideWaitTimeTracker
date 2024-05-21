package com.dallinkooyman.disneyridetimecomparison.data.ride

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dallinkooyman.disneyridetimecomparison.model.Ride

@Entity(tableName = "rides")
data class RideEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val totalWaitTime: Int = 0,
    val parentId: String,
    val latitude: Double,
    val longitude: Double,
    val hasInteractable: Int = 0,
    val totalTimeUntilFirstInteractable: Int = 0
)

fun RideEntity.toRideModel(): Ride {
    val ride = Ride()

    ride.id = id
    ride.rideName = name
    ride.totalWaitTime = totalWaitTime
    ride.latitude = latitude
    ride.longitude = longitude
    ride.hasInteractable = hasInteractable == 1
    ride.parentId = parentId
    ride.setTotalTimeUntilFirstInteractable(totalTimeUntilFirstInteractable)

    return ride
}