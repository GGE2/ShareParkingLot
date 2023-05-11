package com.team.parking.data.model.favorite

data class FavoriteListResponse(
    val feeBasic: Int,
    val jibun: String,
    val lat: Int,
    val lng: Int,
    val meter: Int,
    val parkId: Int,
    val parkingField: Int,
    val parkingName: String,
    val payType: String
)