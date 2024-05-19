package com.dallinkooyman.disneyridetimecomparison.data.rideEvent

class RideEventRepository (private val rideEventDao: RideEventDao) {

    suspend fun insertRideEvent(rideEventEntity: RideEventEntity) = rideEventDao.insert(rideEventEntity)

    suspend fun deleteRideEvent(rideEventEntity: RideEventEntity) = rideEventDao.insert(rideEventEntity)

    suspend fun updateRideEvent(rideEventEntity: RideEventEntity) = rideEventDao.insert(rideEventEntity)

    fun getAllRideEvents() = rideEventDao.getAllRideEvents()
    fun getRideEvents(id: Int) = rideEventDao.getRideEvent(id)

}