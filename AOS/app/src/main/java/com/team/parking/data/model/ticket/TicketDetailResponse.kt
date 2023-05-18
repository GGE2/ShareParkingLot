package com.team.parking.data.model.ticket

data class TicketDetailResponse(
    val address: String,
    val buyConfirm: Boolean,
    val carNumber: String,
    val cost: Int,
    val images: List<String>,
    val inTiming: Int,
    val nickname: String,
    val outTime: Int,
    val parkingDate: String,
    val parkingRegion: String,
    val sellConfirm: Boolean,
    val ticketId: Long,
    val type: Int,
    val buyerNumber: String,
    val sellerNumber: String
)