package com.dallinkooyman.disneyridetimecomparison.model

class Ride(val rideName: String = "Decked Out") {

    var apiWaitTime: Int = -1

    //A fun stat to track, not all rides have them
    var hasInteractable: Boolean = false

    var ridePostedWaitTime: Int = -3
    var currentPostedWaitTime: Int = -5
    var totalWaitTime: Int = -7

    private var rideHistory: MutableSet<RideHistory> = mutableSetOf()

    private var totalTimeUntilFirstInteractable: Int? = null

    fun addRide(ride: RideHistory){
        rideHistory.add(ride)
        totalWaitTime += ride.timeWaited
        if (ride.timeUntilInteractable >= 0){
            totalTimeUntilFirstInteractable = totalTimeUntilFirstInteractable?.plus(ride.timeUntilInteractable)
        }
    }

    fun getRideHistory(): MutableSet<RideHistory> {
        return rideHistory
    }

    fun getTotalTimeUntilFirstInteractable(): Int? {
        return totalTimeUntilFirstInteractable
    }

}