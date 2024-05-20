package com.dallinkooyman.disneyridetimecomparison.data.ride

import com.dallinkooyman.disneyridetimecomparison.model.Ride
import com.dallinkooyman.disneyridetimecomparison.model.RideEvent

data class RideUiState(
    val currentRide: Ride? = null,
    val currentRideEvent: RideEvent = RideEvent()
)
