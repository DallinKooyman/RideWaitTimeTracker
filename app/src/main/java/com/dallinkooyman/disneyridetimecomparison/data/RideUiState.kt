package com.dallinkooyman.disneyridetimecomparison.data

import com.dallinkooyman.disneyridetimecomparison.model.Ride
import com.dallinkooyman.disneyridetimecomparison.model.RideEvent

data class RideUiState(

    val currentRide: Ride = Ride(),
    val currentRideEvent: RideEvent = RideEvent()

)
