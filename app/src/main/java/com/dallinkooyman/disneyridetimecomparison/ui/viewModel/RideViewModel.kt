package com.dallinkooyman.disneyridetimecomparison.ui.viewModel

import androidx.lifecycle.ViewModel
import com.dallinkooyman.disneyridetimecomparison.data.ride.RideUiState
import com.dallinkooyman.disneyridetimecomparison.model.Ride
import com.dallinkooyman.disneyridetimecomparison.model.RideEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RideViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RideUiState())
    val uiState: StateFlow<RideUiState> = _uiState.asStateFlow()

    fun updateCurrentRide(ride: Ride, rideEvent: RideEvent){
        if (rideEvent.validRideEvent()){
            ride.addRideEvent(rideEvent)
            _uiState.update {
                it.copy(
                    currentRide = ride,
                    currentRideEvent = rideEvent
                )
            }
        }
    }

    fun updateCurrentRideEvent(rideEvent: RideEvent){
        if (rideEvent.validRideEvent()) {
            _uiState.update {
                it.copy(
                    currentRideEvent = rideEvent,
                )
            }
        }
    }
}