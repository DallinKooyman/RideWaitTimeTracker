package com.dallinkooyman.disneyridetimecomparison.model

import java.util.Objects

class RideEvent {

    var rideName: String = "Decked Out"
    var enteredLineTime: Long = 0L
    var enteredRideTime: Long = 1L
    var apiPostedTime: Int? = null
    var ridePostedWaitTime: Int? = null
    var timeWaited: Int? = null

    var hasInteractable: Boolean = false
    var timeUntilInteractable: Int? = null

    //A fun stat to track
    var apiAndPostedTimeAreSame: Boolean = true

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
        if (other !is RideEvent){
            return false
        }
        return this.enteredLineTime == other.enteredLineTime && this.enteredRideTime == other.enteredRideTime
    }

    override fun hashCode(): Int {
        return Objects.hash(enteredLineTime, enteredRideTime)
    }

    fun validRideEvent(): Boolean {
        return rideName != "Decked Out" && enteredLineTime > 0L && enteredRideTime >= 1L &&
                apiPostedTime != null && (apiAndPostedTimeAreSame || ridePostedWaitTime != null)
                && timeWaited != null
    }

    // If the time waited is null then return null
    // If apiDifferenceInMinutes is null then it hasn't been calculated successfully
    // If apiPostedTime is null then apiDifferenceInMinutes will continue to be null
    // If timeWaited is null then apiDifferenceInMinutes will continue to be null
    fun getApiDifferenceInMinutes(): Int? {
        if (apiDifferenceInMinutes == null) {
            apiDifferenceInMinutes = apiPostedTime?.let { timeWaited?.minus(it) };
        }

        return apiDifferenceInMinutes
    }

    fun getRidePostedDifferenceInMinutes(): Int? {
        if (ridePostedDifferenceInMinutes == null) {
            ridePostedDifferenceInMinutes = ridePostedWaitTime?.let { timeWaited?.minus(it) };
        }

        return ridePostedDifferenceInMinutes
    }

    fun getApiToPostedDifferenceInMinutes(): Int? {
        if (apiToPostedDifferenceInMinutes == null) {
            apiToPostedDifferenceInMinutes = apiPostedTime?.let { ridePostedWaitTime?.minus(it) };
        }

        return apiToPostedDifferenceInMinutes
    }

    fun getApiToWaitDifferenceInPercent(): Double? {
        if (apiToWaitDifferenceInPercent == null) {
            val timeWaitedAsDouble = timeWaited?.toDouble()
            val apiPostedTimeAsDouble = apiPostedTime?.toDouble()
            if (timeWaitedAsDouble == null || apiPostedTimeAsDouble == null){
                return null
            }
            apiToWaitDifferenceInPercent = String.format("%.2f", timeWaitedAsDouble / apiPostedTimeAsDouble * 100).toDouble()
        }

        return apiToWaitDifferenceInPercent
    }

    fun getRideToWaitDifferenceInPercent(): Double? {
        if (rideToWaitDifferenceInPercent == null) {
            val timeWaitedAsDouble = timeWaited?.toDouble()
            val ridePostedWaitTimeAsDouble = ridePostedWaitTime?.toDouble()
            if (timeWaitedAsDouble == null || ridePostedWaitTimeAsDouble == null){
                return null
            }
            rideToWaitDifferenceInPercent = String.format("%.2f", timeWaitedAsDouble / ridePostedWaitTimeAsDouble * 100).toDouble()
        }

        return rideToWaitDifferenceInPercent
    }

    fun getApiToRideDifferenceInPercent(): Double? {
        if (apiToRideDifferenceInPercent == null) {
            val apiPostedTimeAsDouble = apiPostedTime?.toDouble()
            val ridePostedWaitTimeAsDouble = ridePostedWaitTime?.toDouble()
            if (apiPostedTimeAsDouble == null || ridePostedWaitTimeAsDouble == null){
                return null
            }
            apiToRideDifferenceInPercent = String.format("%.2f", ridePostedWaitTimeAsDouble / apiPostedTimeAsDouble * 100).toDouble()
        }

        return apiToRideDifferenceInPercent
    }

}
