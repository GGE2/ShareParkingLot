package com.team.parking.data.model.parkinglot

data class ParkingLotResponse (
    val dayFee: Int?,
    val feeBasic: Int?,
    val feeData: String?,
    val imageUrl: List<String>?,
    val jibun: String,
    val lotName: String,
    val lotType: String?,
    val openDay: String?,
    val parkId: Long,
    val payType: String,
    val roadAddr: String?,
    val specialProp: String?
)