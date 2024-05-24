package com.dallinkooyman.disneyridetimecomparison.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dallinkooyman.disneyridetimecomparison.data.rideEvent.RideEventRepository
import com.dallinkooyman.disneyridetimecomparison.data.rideEvent.toRideEventModel
import com.dallinkooyman.disneyridetimecomparison.model.RideEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RideHistoryViewModel(
    rideEventRepository: RideEventRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(RideHistoryUiState())
    val uiState: StateFlow<RideHistoryUiState> =
        rideEventRepository.getAllRideEvents().map {
            RideHistoryUiState(
                it.map{ rideEventEntity ->
                    rideEventEntity.toRideEventModel()
                }
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = RideHistoryUiState()
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}


data class RideHistoryUiState(
    var rideHistory: List<RideEvent> = listOf()
)