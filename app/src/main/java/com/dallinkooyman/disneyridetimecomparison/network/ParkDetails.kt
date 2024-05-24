package com.dallinkooyman.disneyridetimecomparison.network

import kotlinx.serialization.Serializable

@Serializable
data class ParkDetails(
    val id: String,
    val name: String,
    val entityType: String,
    val timezone: String,
    val liveData: List<LiveData>
)
