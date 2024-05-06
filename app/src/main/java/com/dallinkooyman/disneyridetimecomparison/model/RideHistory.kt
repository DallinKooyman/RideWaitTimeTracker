package com.dallinkooyman.disneyridetimecomparison.model

import java.util.Objects

class RideHistory {

    var enteredLineTime: Long = 0L
    var enteredRideTime: Long = 1L
    var apiPostedTime: Int = -2
    var ridePostedWaitTime: Int = -4
    var timeWaited: Int = -8
    var timeUntilInteractable: Int = -10

    //A fun stat to track
    private var apiAndRideAreSame: Boolean = false

    //A positive difference means the wait time was longer than the posted time
    private var apiDifferenceInMinutes: Int? = null
    private var ridePostedDifferenceInMinutes: Int? = null

    //A positive difference means the ride time was longer time than the API
    private var apiToPostedDifferenceInMinutes: Int? = null

    //A percent > 100 means the wait time was longer that the posted time
    private var apiToWaitDifferenceInPercent: Double? = null
    private var rideToWaitDifferenceInPercent: Double? = null

    //A percent > 100 means that the ride time was longer than the API
    private var apiToRideDifferenceInPercent: Double? = null

    override fun equals(other: Any?): Boolean {
        if (other !is RideHistory){
            return false
        }
        return this.enteredLineTime == other.enteredLineTime && this.enteredRideTime == other.enteredRideTime
    }

    override fun hashCode(): Int {
        return Objects.hash(enteredLineTime, enteredRideTime)
    }


    fun getApiDifferenceInMinutes(): Int? {
        if (timeWaited < 0) {
            return null
        }
        if (apiDifferenceInMinutes == null) {
            apiDifferenceInMinutes = timeWaited - apiPostedTime;
        }

        return apiDifferenceInMinutes
    }

    fun getRidePostedDifferenceInMinutes(): Int? {
        if (timeWaited < 0) {
            return null
        }
        if (ridePostedDifferenceInMinutes == null) {
            ridePostedDifferenceInMinutes = timeWaited - ridePostedWaitTime;
        }

        return ridePostedDifferenceInMinutes
    }

    fun getApiToPostedDifferenceInMinutes(): Int? {
        if (timeWaited < 0) {
            return null
        }
        if (apiToPostedDifferenceInMinutes == null) {
            apiToPostedDifferenceInMinutes = ridePostedWaitTime - apiPostedTime;
        }

        return apiToPostedDifferenceInMinutes
    }

    fun getApiToWaitDifferenceInPercent(): Double? {
        if (apiPostedTime == 0) {
            return null
        }
        if (apiToWaitDifferenceInPercent == null) {
            apiToWaitDifferenceInPercent = String.format("%.2f", timeWaited.toDouble() / apiPostedTime.toDouble() * 100).toDouble()
        }

        return apiToWaitDifferenceInPercent
    }

    fun getRideToWaitDifferenceInPercent(): Double? {
        if (ridePostedWaitTime == 0) {
            return null
        }
        if (rideToWaitDifferenceInPercent == null) {
            rideToWaitDifferenceInPercent = String.format("%.2f", timeWaited.toDouble() / ridePostedWaitTime.toDouble() * 100).toDouble()
        }

        return rideToWaitDifferenceInPercent
    }

    fun getApiToRideDifferenceInPercent(): Double? {
        if (apiPostedTime == 0) {
            return null
        }
        if (apiToRideDifferenceInPercent == null) {
            apiToRideDifferenceInPercent = String.format("%.2f", ridePostedWaitTime.toDouble() / apiPostedTime.toDouble() * 100).toDouble()
        }

        return apiToRideDifferenceInPercent
    }

}
