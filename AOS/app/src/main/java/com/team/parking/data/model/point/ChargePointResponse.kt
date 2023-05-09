package com.team.parking.data.model.point

data class ChargePointResponse (
    var nickname: String,
    var ptCharge: Int,
    var ptHas: Int,
    var userId: Long
)