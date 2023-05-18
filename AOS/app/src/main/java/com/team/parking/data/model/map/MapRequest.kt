package com.team.parking.data.model.map


import com.google.gson.annotations.SerializedName

data class MapRequest(
    @SerializedName("centerLat")
    val centerLat: Double,
    @SerializedName("centerLng")
    val centerLng: Double,
    @SerializedName("endLat")
    val endLat: Double,
    @SerializedName("endLng")
    val endLng: Double,
    @SerializedName("startLat")
    val startLat: Double,
    @SerializedName("startLng")
    val startLng: Double,
    @SerializedName("zoomLevel")
    val zoomLevel: Double
)