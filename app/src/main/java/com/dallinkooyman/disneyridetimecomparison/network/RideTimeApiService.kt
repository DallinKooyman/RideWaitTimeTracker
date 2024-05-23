package com.dallinkooyman.disneyridetimecomparison.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path


private const val BASE_URL = "https://api.themeparks.wiki/v1/"

private val json = Json {
    ignoreUnknownKeys = true
}

private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json; charset=UTF8".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

object RideTimeApi {
    val retrofitService : RideTimeApiService by lazy {
        retrofit.create(RideTimeApiService::class.java)
    }
}

interface RideTimeApiService {
    @GET("entity/{entityId}/live")
    suspend fun getAllParkWaitTimes(@Path("entityId") entityId: String) : ParkDetails

    @GET("entity/{entityId}/live")
    suspend fun getRideWaitTimes(@Path("entityId") entityId: String) : RideDetails
}
