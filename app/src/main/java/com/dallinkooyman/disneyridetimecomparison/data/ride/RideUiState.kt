package com.dallinkooyman.disneyridetimecomparison.data.ride

import com.dallinkooyman.disneyridetimecomparison.model.Ride
import com.dallinkooyman.disneyridetimecomparison.model.RideEvent

data class RideUiState(
    var currentRide: Ride? = null,
    var currentRideEvent: RideEvent? = null,
    var allRides: List<Ride> = listOf()
)
