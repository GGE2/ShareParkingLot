package com.team.parking.data.model.ticket

data class TicketBuyConfirmResponse(
    val buyDate: String,
    val creditId: Int,
    val inTiming: Int,
    val outTiming: Int,
    val parkingRegion: String,
    val ptHas: Int,
    val ptLose: Int,
    val shaId: Long,
    val type: Int
)