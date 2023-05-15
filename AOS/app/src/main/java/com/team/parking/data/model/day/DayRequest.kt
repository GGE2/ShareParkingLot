package com.team.parking.data.model.day

data class DayRequest (
    var dayStart: Int,
    var dayEnd: Int,
    var dayStr: String,
    var enable: Boolean
)