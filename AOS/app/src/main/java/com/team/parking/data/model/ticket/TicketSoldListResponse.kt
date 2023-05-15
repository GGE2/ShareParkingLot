package com.team.parking.data.model.ticket

data class TicketSoldListResponse(
    val buyConfirm: Boolean,
    val inTiming: Int,
    val outTime: Int,
    val parkingDate: String,
    val parkingRegion: String,
    val ptToEarn: Int,
    val sellConfirm: Boolean,
    val shaId: Long,
    val ticketId: Long
)