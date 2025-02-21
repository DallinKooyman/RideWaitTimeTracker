package com.dallinkooyman.disneyridetimecomparison.model

import com.dallinkooyman.disneyridetimecomparison.data.rideEvent.RideEventEntity
import java.util.Objects

class RideEvent() {

    var eventId: Int = -8
    var rideId: String = ""
    var rideName: String = "Decked Out"
    var enteredLineTime: Long = 2L
    var gotOnRideTime: Long = 3L
    var apiPostedTime: Int? = null
    var timeWaited: Int? = null

    var hasInteractable: Boolean = false
    var timeUntilInteractable: Int? = null

    //A fun stat to track
    var apiAndPostedTimeAreSame: Boolean = true

    private var ridePostedWaitTime: Int? = null

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

    constructor(
        eventId: Int,
        rideId: String,
        rideName: String,
        enteredLineTime: Long,
        gotOnRideTime: Long,
        apiPostedTime: Int?,
        ridePostedTime: Int?,
        timeWaited: Int?,
        hasInteractable: Boolean,
        timeUntilInteractable: Int?,
        apiAndPostedTimeAreSame: Boolean
    ) : this() {
        this.eventId = eventId
        this.rideId = rideId
        this.rideName = rideName
        this.enteredLineTime = enteredLineTime
        this.gotOnRideTime = gotOnRideTime
        this.apiPostedTime = apiPostedTime
        this.ridePostedWaitTime = ridePostedTime
        this.timeWaited = timeWaited
        this.hasInteractable = hasInteractable
        this.timeUntilInteractable = timeUntilInteractable
        this.apiAndPostedTimeAreSame = apiAndPostedTimeAreSame
    }

    override fun equals(other: Any?): Boolean {
        if (other !is RideEvent){
            return false
        }
        return this.enteredLineTime == other.enteredLineTime && this.gotOnRideTime == other.gotOnRideTime
    }

    override fun hashCode(): Int {
        return Objects.hash(enteredLineTime, gotOnRideTime)
    }


    fun setRidePostedWaitTime(time: Int){
        if (time >= 0){
            this.ridePostedWaitTime = time
        }
        apiAndPostedTimeAreSame = this.apiPostedTime == this.ridePostedWaitTime
    }

    fun getRidePostedWaitTime(): Int? {
        return this.ridePostedWaitTime
    }

    fun validRideEvent(): Boolean {
        return rideName != "Decked Out" && enteredLineTime > 0L && gotOnRideTime >= 1L &&
                apiPostedTime != null && (apiAndPostedTimeAreSame || ridePostedWaitTime != null)
                && timeWaited != null
    }

    fun calculateStats(){
        getApiDifferenceInMinutes()
        getRidePostedDifferenceInMinutes()
        getApiToPostedDifferenceInMinutes()
        getApiToWaitDifferenceInPercent()
        getRideToWaitDifferenceInPercent()
        getApiToRideDifferenceInPercent()
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

    fun convertToRideEventEntity() :RideEventEntity {
        calculateStats()
        return RideEventEntity(
            rideId = rideId,
            rideName = rideName,
            enteredLineTime = enteredLineTime,
            gotOnRideTime = gotOnRideTime,
            apiPostedTimeForEvent =  apiPostedTime?: -1,
            apiAndPostedAreSame = if (this.apiAndPostedTimeAreSame) 1 else 0,
            ridePostedTime = ridePostedWaitTime,
            timeWaited = timeWaited?: -1,
            hasInteractable = hasInteractable,
            timeUntilInteractable = timeUntilInteractable?: -1,
            apiDifferenceInMinutes = apiDifferenceInMinutes?: 100,
            ridePostedDifferenceInMinutes = ridePostedDifferenceInMinutes?: 100,
            apiToPostedDifferenceInMinutes = apiToPostedDifferenceInMinutes?: 100,
            apiToWaitDifferenceInPercent = apiToWaitDifferenceInPercent?: 100.0,
            rideToWaitDifferenceInPercent = rideToWaitDifferenceInPercent?: 100.0,
            apiToRideDifferenceInPercent = apiToRideDifferenceInPercent?: 100.0
        )
    }

    fun convertToRideEventEntityWithEventId(): RideEventEntity {
        calculateStats()
        return RideEventEntity(
            id = eventId,
            rideId = rideId,
            rideName = rideName,
            enteredLineTime = enteredLineTime,
            gotOnRideTime = gotOnRideTime,
            apiPostedTimeForEvent =  apiPostedTime?: -1,
            apiAndPostedAreSame = if (this.apiAndPostedTimeAreSame) 1 else 0,
            ridePostedTime = ridePostedWaitTime,
            timeWaited = timeWaited?: -1,
            hasInteractable = hasInteractable,
            timeUntilInteractable = timeUntilInteractable?: -1,
            apiDifferenceInMinutes = apiDifferenceInMinutes?: 100,
            ridePostedDifferenceInMinutes = ridePostedDifferenceInMinutes?: 100,
            apiToPostedDifferenceInMinutes = apiToPostedDifferenceInMinutes?: 100,
            apiToWaitDifferenceInPercent = apiToWaitDifferenceInPercent?: 100.0,
            rideToWaitDifferenceInPercent = rideToWaitDifferenceInPercent?: 100.0,
            apiToRideDifferenceInPercent = apiToRideDifferenceInPercent?: 100.0
        )
    }

}
