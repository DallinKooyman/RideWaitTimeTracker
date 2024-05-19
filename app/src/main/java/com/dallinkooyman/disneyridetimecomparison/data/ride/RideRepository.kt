package com.dallinkooyman.disneyridetimecomparison.data.ride

class RideRepository(private val rideDao: RideDao) {

    suspend fun insertRide(rideEntity: RideEntity) = rideDao.insert(rideEntity)

    suspend fun deleteRide(rideEntity: RideEntity) = rideDao.insert(rideEntity)

    suspend fun updateRide(rideEntity: RideEntity) = rideDao.insert(rideEntity)

    fun getRide(id: Int) = rideDao.getRide(id)

}