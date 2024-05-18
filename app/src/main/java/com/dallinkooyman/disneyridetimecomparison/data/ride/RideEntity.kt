package com.dallinkooyman.disneyridetimecomparison.data.ride

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rides")
data class RideEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val totalWaitTime: Int,
    val lat: Double,
    val longitude: Double,
    val hasInteractable: Boolean,
    val totalTimeUntilFirstInteractable: Int
)