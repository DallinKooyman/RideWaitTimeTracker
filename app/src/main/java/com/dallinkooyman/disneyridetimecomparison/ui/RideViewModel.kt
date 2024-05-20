package com.dallinkooyman.disneyridetimecomparison.ui

import androidx.lifecycle.ViewModel
import com.dallinkooyman.disneyridetimecomparison.data.ride.RideRepository
import com.dallinkooyman.disneyridetimecomparison.data.ride.RideUiState
import com.dallinkooyman.disneyridetimecomparison.data.rideEvent.RideEventRepository
import com.dallinkooyman.disneyridetimecomparison.model.Ride
import com.dallinkooyman.disneyridetimecomparison.model.RideEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RideViewModel(
    private val rideRepository: RideRepository,
    private val rideEventRepository: RideEventRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RideUiState())
    val uiState: StateFlow<RideUiState> = _uiState.asStateFlow()

    fun updateRideInUiState(ride: Ride){
        _uiState.update {
            it.copy(
                currentRide = ride,
            )
        }
    }

    fun updateRideEventInUiState(rideEvent: RideEvent){
        _uiState.update {
            it.copy(
                currentRideEvent = rideEvent,
            )
        }
    }

    private fun validateInput(): Boolean {
        return uiState.value.currentRide != null
    }

    suspend fun saveRide(){
        if (validateInput()){
            rideRepository.insertRide(uiState.value.currentRide!!.convertToRideEntity())
        }
    }

    suspend fun updateRide(){
        if (validateInput()) {
            rideRepository.updateRide(uiState.value.currentRide!!.convertToRideEntity())
        }
    }

    suspend fun deleteRide(){
        if (validateInput()) {
            rideRepository.deleteRide(uiState.value.currentRide!!.convertToRideEntity())
        }
    }

    private fun validateRideEventInput(): Boolean{
        return uiState.value.currentRideEvent.validRideEvent()
    }

    suspend fun saveRideEvent(){
        if (validateRideEventInput()) {
            rideEventRepository.insertRideEvent(uiState.value.currentRideEvent.convertToRideEventEntity())
        }
    }

    suspend fun updateRideEvent(){
        if (validateRideEventInput()) {
            rideEventRepository.updateRideEvent(uiState.value.currentRideEvent.convertToRideEventEntityWithEventId())
        }
    }

    suspend fun deleteRideEvent(){
        if (validateRideEventInput()){
            rideEventRepository.deleteRideEvent(uiState.value.currentRideEvent.convertToRideEventEntityWithEventId())
        }
    }

}