package com.dallinkooyman.disneyridetimecomparison.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dallinkooyman.disneyridetimecomparison.Constants
import com.dallinkooyman.disneyridetimecomparison.data.ride.RideRepository
import com.dallinkooyman.disneyridetimecomparison.data.ride.RideUiState
import com.dallinkooyman.disneyridetimecomparison.data.ride.toRideModel
import com.dallinkooyman.disneyridetimecomparison.data.rideEvent.RideEventRepository
import com.dallinkooyman.disneyridetimecomparison.model.Ride
import com.dallinkooyman.disneyridetimecomparison.model.RideEvent
import com.dallinkooyman.disneyridetimecomparison.network.RideTimeApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Timer
import kotlin.concurrent.timerTask

class RideViewModel(
    private val rideRepository: RideRepository,
    private val rideEventRepository: RideEventRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RideUiState())
    val uiState: StateFlow<RideUiState> = _uiState.asStateFlow()

    private var timer: Timer? = null
    var waited = -1
        get() {
            val currentTime = ZonedDateTime.now(ZoneId.systemDefault()).toEpochSecond()
            this.waited = ((currentTime - _uiState.value.currentRideEvent!!.enteredLineTime) / 60).toInt()
            return field
        }

    init {
        viewModelScope.launch {
            try {
                val rideDetailList = RideTimeApi.retrofitService
                    .getAllParkWaitTimes(Constants.DISNEY_LAND_RESORT_ID)
                    .liveData.filter {
                        it.entityType == "ATTRACTION" && it.status == "OPERATING"
                    }

                rideRepository.getAllRides().collect{ allRides ->
                    val rideModels = allRides.map { it.toRideModel() }
                    rideModels.forEach { ride ->
                        Log.i("RideDB", ride.rideName + "\n" + ride.id)
                        rideDetailList.forEach{ detail ->
                            Log.i("detail", detail.name + "\n" + detail.id)
                            if (ride.id == detail.id && detail.queue != null &&
                                detail.queue.standby != null && detail.queue.standby.waitTime != null){
                                Log.i(
                                    "Inside",
                                    ride.apiWaitTime.toString() + "\n" + ride.rideName
                                )
                                Log.i(
                                    "Inside",
                                    detail.queue.standby.waitTime.toString() + "\n" + ride.rideName
                                )

                                ride.apiWaitTime = detail.queue.standby.waitTime
                            }
                        }
                        Log.i("RideDB", ride.apiWaitTime.toString())
                    }

                    _uiState.update { state ->
                        state.copy(
                            allRides = rideModels
                        )
                    }
                }
            } catch (e: Exception){
                Log.e("API", e.toString())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }

    fun startTimer() {
        if (timer == null) {
            timer = Timer().apply {
                scheduleAtFixedRate(timerTask {
                    val rideEvent = _uiState.value.currentRideEvent!!
                    rideEvent.timeWaited = waited
                    updateRideEventInUiState(rideEvent)
                }, 60000, 60000) // Schedule to run every minute
            }
        }
    }

    fun stopTimer() {
        timer?.cancel()
        timer = null
        waited = -1
    }

    private fun getAllRideTimes() {
        viewModelScope.launch {

        }
    }


    fun updateCurrentRideInUiState(ride: Ride?){
        _uiState.update {
            it.copy(
                currentRide = ride,
            )
        }
    }

    fun addRideEventInUiState(rideEvent: RideEvent){
        startTimer()
        _uiState.update {
            it.copy(
                currentRideEvent = rideEvent,
            )
        }
    }

    fun updateRideEventInUiState(rideEvent: RideEvent){
        _uiState.update {
            it.copy(
                currentRideEvent = rideEvent,
            )
        }
    }

    private fun validateInput(): Boolean {
        return uiState.value.currentRide != null
    }

    suspend fun saveRide(){
        if (validateInput()){
            rideRepository.insertRide(uiState.value.currentRide!!.convertToRideEntity())
        }
    }

    suspend fun updateRide(){
        if (validateInput()) {
            rideRepository.updateRide(uiState.value.currentRide!!.convertToRideEntity())
        }
    }

    fun getRideByName(name: String) {
        viewModelScope.launch {
            rideRepository.getRideByName(name).collect{ ride ->
                if (ride != null) {
                    _uiState.update {
                        it.copy(
                            currentRide = ride.toRideModel()
                        )
                    }
                }
            }
        }
    }

    suspend fun deleteRide(){
        if (validateInput()) {
            rideRepository.deleteRide(uiState.value.currentRide!!.convertToRideEntity())
        }
    }

    private fun validateRideEventInput(): Boolean{
        return uiState.value.currentRideEvent!!.validRideEvent()
    }

    suspend fun saveRideEvent(){
        if (validateRideEventInput()) {
            rideEventRepository.insertRideEvent(uiState.value.currentRideEvent!!.convertToRideEventEntity())
        }

        uiState.value.currentRide!!.totalWaitTime += uiState.value.currentRideEvent!!.timeWaited!!

        this.saveRide()
        _uiState.update {
            it.copy(
                currentRideEvent = null,
                currentRide = null
            )
        }
        stopTimer()
    }

    suspend fun updateRideEvent(){
        if (validateRideEventInput()) {
            rideEventRepository.updateRideEvent(uiState.value.currentRideEvent!!.convertToRideEventEntityWithEventId())
        }
    }

    suspend fun deleteRideEvent(){
        if (validateRideEventInput()){
            rideEventRepository.deleteRideEvent(uiState.value.currentRideEvent!!.convertToRideEventEntityWithEventId())
        }
    }

}