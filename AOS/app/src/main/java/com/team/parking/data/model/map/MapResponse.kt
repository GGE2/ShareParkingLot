package com.team.parking.data.model.map


import com.google.gson.annotations.SerializedName

data class MapResponse(
    @SerializedName("feeBasic")
    val feeBasic: Int,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double,
    @SerializedName("parkType")
    val parkType: Int,
    @SerializedName("clusteringCnt")
    val clusteringCnt : Int
)