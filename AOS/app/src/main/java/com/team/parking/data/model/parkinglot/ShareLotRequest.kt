package com.team.parking.data.model.parkinglot

data class ShareLotRequest (
    var shaType: Int,
    var shaName: String,
    var jibun: String,
    var road: String,
    var shaField: Int = 1,
    var shaFee: Int,
    var shaProp: String?,
    var latitude: Float,
    var longitude: Float,
    var userId: Long
)