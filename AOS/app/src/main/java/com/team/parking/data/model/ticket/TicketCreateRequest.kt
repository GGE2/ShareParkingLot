package com.team.parking.data.model.ticket

data class TicketCreateRequest(
    var carNumber: String,
    var inTiming: Int,
    var shaId: Long,
    var type: Int
)