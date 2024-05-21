package com.dallinkooyman.disneyridetimecomparison.model

import com.dallinkooyman.disneyridetimecomparison.data.ride.RideEntity

class Ride() {

    var id: String = ""
    var rideName: String = "Decked Out"
    var apiWaitTime: Int = -1
    var totalWaitTime: Int = -3
    var latitude: Double = 1000.0
    var longitude: Double = 2000.0
    var parentId: String = ""

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

    fun setTotalTimeUntilFirstInteractable(time: Int) {
        totalTimeUntilFirstInteractable = time
    }
    fun getTotalTimeUntilFirstInteractable(): Int? {
        return totalTimeUntilFirstInteractable
    }

    fun convertToRideEntity(): RideEntity {
        return RideEntity(
            id = id,
            name = rideName,
            totalWaitTime = totalWaitTime,
            latitude = latitude,
            longitude = longitude,
            hasInteractable = if (hasInteractable) 0 else 1,
            parentId = parentId,
            totalTimeUntilFirstInteractable = totalTimeUntilFirstInteractable?: -1
        )
    }

}