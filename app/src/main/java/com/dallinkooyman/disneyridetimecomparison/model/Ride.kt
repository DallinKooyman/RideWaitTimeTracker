package com.dallinkooyman.disneyridetimecomparison.model

class Ride(val rideName: String = "Decked Out") {

    var apiWaitTime: Int = -1
    var ridePostedWaitTime: Int = -3
    var currentApiWaitTime: Int = -5
    var totalWaitTime: Int = -7

    //A fun stat to track, not all rides have them
    var hasInteractable: Boolean = false

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

    fun setTotalTotalTimeUntilFirstInteractable(time: Int) {
        if (hasInteractable) {
            totalTimeUntilFirstInteractable = time
        }
    }
    fun getTotalTimeUntilFirstInteractable(): Int? {
        return totalTimeUntilFirstInteractable
    }

}