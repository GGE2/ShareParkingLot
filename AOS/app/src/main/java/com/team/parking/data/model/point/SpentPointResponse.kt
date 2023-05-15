package com.team.parking.data.model.point

data class SpentPointResponse(
    val creditId: Int,
    val lotName: String,
    val ptLose: Int,
    val transactionDate: String,
    val type: Int
)