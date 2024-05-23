package com.dallinkooyman.disneyridetimecomparison.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RideDetails(
    val id: String,
    val name: String,
    val entityType: String,
    val parkId: String?,
    val externalId: String,
    val queue: Queue? = null,
    val status: String,
    val lastUpdated: String
)

@Serializable
data class Queue(
    @SerialName("STANDBY")
    val standby: Standby? = null
)

@Serializable
data class Standby(
    val waitTime: Int? = null
)