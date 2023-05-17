package com.team.parking.data.model.notification

class GetNotiListRequest (
    val noti_id: Long? = null,
    val user_id: Long? = null,
    val ticket_id: Long? = null,
    val title: String? = null,
    val content: String? = null,
    val type :Int
)