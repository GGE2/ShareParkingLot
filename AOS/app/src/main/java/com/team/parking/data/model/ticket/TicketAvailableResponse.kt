package com.team.parking.data.model.ticket

data class TicketAvailableResponse(
    val allDay: Boolean,
    val fiveHours: Boolean,
    val oneHour: Boolean,
    val threeHours: Boolean
)