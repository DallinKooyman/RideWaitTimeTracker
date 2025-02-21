package com.dallinkooyman.disneyridetimecomparison.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dallinkooyman.disneyridetimecomparison.ui.AppViewModelProvider
import com.dallinkooyman.disneyridetimecomparison.ui.RideViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: RideViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val rideEvent = uiState.currentRideEvent

    if (rideEvent == null){
        GettingInLineScreen(
            uiState = uiState,
            allRides = uiState.allRides,
            updateStateCurrentRide = viewModel::updateCurrentRideInUiState,
            onConfirmInLine = viewModel::addRideEventInUiState,
            onCancel = viewModel::removeRide,
            modifier = modifier
        )
    }
    else {
        WaitingInLineScreen(
            rideEvent = rideEvent,
            onChange = viewModel::updateRideEventInUiState,
            onConfirmedOnRide = {
                coroutineScope.launch {
                    viewModel.saveRideEvent()
                }
            },
            modifier = modifier
        )
    }
}