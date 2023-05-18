package com.team.parking.data.model.ticket

data class TicketCreateResponse(
    val address: String,
    val buyer: String,
    val carNumber: String,
    val cost: Int,
    val inTiming: Int,
    val outTime: Int,
    val parkingRegion: String,
    val shaId: Long,
    val ticketId: Long,
    val type: Int
)