package com.dallinkooyman.disneyridetimecomparison.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.dallinkooyman.disneyridetimecomparison.DisneyRideTimerComparisonApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            RideViewModel(
                inventoryApplication().container.rideRepository,
                inventoryApplication().container.rideEventRepository
            )
        }
    }
}

fun CreationExtras.inventoryApplication(): DisneyRideTimerComparisonApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as DisneyRideTimerComparisonApplication)