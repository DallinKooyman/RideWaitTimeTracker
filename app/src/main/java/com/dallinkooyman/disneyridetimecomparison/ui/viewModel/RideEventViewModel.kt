package com.dallinkooyman.disneyridetimecomparison.ui.viewModel

import androidx.lifecycle.ViewModel
import com.dallinkooyman.disneyridetimecomparison.data.rideEvent.RideEventRepository
import com.dallinkooyman.disneyridetimecomparison.data.rideEvent.RideEventUiState
import com.dallinkooyman.disneyridetimecomparison.model.RideEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RideEventViewModel(private val rideEventRepository: RideEventRepository): ViewModel() {

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
        return uiState.value.currentRideEvent?.validRideEvent() ?: false
    }

    suspend fun saveRideEvent(){
        if (validateInput()) {
            rideEventRepository.insertRideEvent(uiState.value.currentRideEvent!!.convertToRideEventEntity())
        }
    }

    suspend fun updateRideEvent(){
        if (validateInput()) {
            rideEventRepository.updateRideEvent(uiState.value.currentRideEvent!!.convertToRideEventEntityWithEventId())
        }
    }

    suspend fun deleteRideEvent(){
        if (validateInput()){
            rideEventRepository.deleteRideEvent(uiState.value.currentRideEvent!!.convertToRideEventEntityWithEventId())
        }
    }

}