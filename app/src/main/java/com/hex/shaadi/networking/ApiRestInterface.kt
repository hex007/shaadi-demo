package com.hex.shaadi.networking

import com.hex.shaadi.model.NetworkProfileResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRestInterface {

    @GET("api")
    suspend fun getProfiles(@Query("results") maxProfiles: Int): NetworkProfileResponse
}