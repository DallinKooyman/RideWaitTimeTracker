package com.dallinkooyman.disneyridetimecomparison.ui.viewModel

import android.text.Spannable.Factory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.dallinkooyman.disneyridetimecomparison.data.ride.RideDao
import com.dallinkooyman.disneyridetimecomparison.data.ride.RideRepository
import com.dallinkooyman.disneyridetimecomparison.data.ride.RideUiState
import com.dallinkooyman.disneyridetimecomparison.model.Ride
import com.dallinkooyman.disneyridetimecomparison.model.RideEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RideViewModel(private val rideRepository: RideRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(RideUiState())
    val uiState: StateFlow<RideUiState> = _uiState.asStateFlow()

    fun updateUiState(ride: Ride){
        _uiState.update {
            it.copy(
                currentRide = ride,
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

    suspend fun updateRideEvent(){
        if (validateInput()) {
            rideRepository.updateRide(uiState.value.currentRide!!.convertToRideEntity())
        }
    }

    suspend fun deleteRideEvent(){
        if (validateInput()) {
            rideRepository.deleteRide(uiState.value.currentRide!!.convertToRideEntity())
        }
    }

}