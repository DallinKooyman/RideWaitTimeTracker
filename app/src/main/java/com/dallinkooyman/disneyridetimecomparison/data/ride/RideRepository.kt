package com.dallinkooyman.disneyridetimecomparison.data.ride

class RideRepository(private val rideDao: RideDao) {

    suspend fun insertRide(rideEntity: RideEntity) = rideDao.insert(rideEntity)

    suspend fun deleteRide(rideEntity: RideEntity) = rideDao.insert(rideEntity)

    suspend fun updateRide(rideEntity: RideEntity) = rideDao.insert(rideEntity)

    suspend fun getRide(id: Int) = rideDao.getRide(id)

    suspend fun getRideByName(name: String) = rideDao.getRideByName(name)

    fun getAllRides() = rideDao.getAllRides()

}