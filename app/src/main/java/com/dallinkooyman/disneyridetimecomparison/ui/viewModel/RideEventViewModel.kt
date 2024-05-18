package com.dallinkooyman.disneyridetimecomparison.ui.viewModel

import androidx.lifecycle.ViewModel
import com.dallinkooyman.disneyridetimecomparison.data.rideEvent.RideEventDao
import com.dallinkooyman.disneyridetimecomparison.data.rideEvent.RideEventUiState
import com.dallinkooyman.disneyridetimecomparison.model.RideEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RideEventViewModel(private val rideEventDao: RideEventDao): ViewModel() {

    private val _uiState = MutableStateFlow(RideEventUiState())
    val uiState: StateFlow<RideEventUiState> = _uiState.asStateFlow()


    fun updateUiState(updatedRideEvent: RideEvent){
        _uiState.update {
            it.copy(
                currentRideEvent = updatedRideEvent
            )
        }
    }

    private fun validateInput(): Boolean{
        return uiState.value.currentRideEvent.validRideEvent();
    }

    suspend fun saveRideEvent(){
        if (validateInput()) {
            rideEventDao.insert(uiState.value.currentRideEvent.convertToRideEventEntity())
        }
    }

    suspend fun updateRideEvent(){
        if (validateInput()) {
            rideEventDao.insert(uiState.value.currentRideEvent.convertToRideEventEntityWithEventId())
        }
    }

    suspend fun deleteRideEvent(){
        if (validateInput()){
            rideEventDao.delete(uiState.value.currentRideEvent.convertToRideEventEntityWithEventId())
        }
    }

}