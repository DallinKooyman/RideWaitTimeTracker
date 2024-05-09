package com.dallinkooyman.disneyridetimecomparison.model

class Ride(private val rideName: String = "Decked Out") {

    var apiWaitTime: Int = -1
    var totalWaitTime: Int = -3

    //A fun stat to track, not all rides have them
    var hasInteractable: Boolean = false
    // -5 For debug reasons
    private var totalTimeUntilFirstInteractable: Int? = null

    private var rideHistory: MutableSet<RideEvent> = mutableSetOf()

    fun addRideEvent(rideEvent: RideEvent){
        if (rideEvent.rideName == this.rideName && rideEvent.validRideEvent()) {
            rideHistory.add(rideEvent)
            totalWaitTime += rideEvent.timeWaited!!
            if (rideEvent.timeUntilInteractable != null) {
                hasInteractable = true
                totalTimeUntilFirstInteractable =
                    totalTimeUntilFirstInteractable?.plus(rideEvent.timeUntilInteractable!!)
            }
        }
    }

    fun getRideHistory(): MutableSet<RideEvent> {
        return rideHistory
    }

    fun getTotalTimeUntilFirstInteractable(): Int? {
        return totalTimeUntilFirstInteractable
    }

}