package com.team.parking.data.model.point

data class EarnedPointResponse(
    val creditId: Int,
    val lotName: String,
    val ptGet: Int,
    val transactionDate: String,
    val type: Int
)