package com.hex.shaadi.model

import com.google.gson.annotations.SerializedName

class NetworkProfileResponse {
    @SerializedName("results")
    val results: List<NetworkProfile> = emptyList()
}
