package com.dallinkooyman.disneyridetimecomparison.data

import android.content.Context
import com.dallinkooyman.disneyridetimecomparison.data.ride.RideDatabase
import com.dallinkooyman.disneyridetimecomparison.data.ride.RideRepository
import com.dallinkooyman.disneyridetimecomparison.data.rideEvent.RideEventDatabase
import com.dallinkooyman.disneyridetimecomparison.data.rideEvent.RideEventRepository

/**
 * App container for Dependency injection.
 */
interface DisneyRideTimerComparisonContainer {
    val rideRepository: RideRepository
    val rideEventRepository: RideEventRepository
}
/**
 * [DisneyRideTimerComparisonDataContainer] implementation that provides instance of [RideRepository]
 * and [RideEventRepository]
 */
class DisneyRideTimerComparisonDataContainer(private val context: Context) : DisneyRideTimerComparisonContainer {

    /**
     * Implementation for [RideRepository]
     */
    override val rideRepository: RideRepository by lazy {
        RideRepository(RideDatabase.getDatabase(context).rideDao())
    }

    /**
     * Implementation for [RideEventRepository]
     */
    override val rideEventRepository: RideEventRepository by lazy {
        RideEventRepository(RideEventDatabase.getDatabase(context).rideEventDao())
    }

}