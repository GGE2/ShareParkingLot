package com.team.parking.data.model.user

data class UpdateFcmTokenRequest (
    val user_id : Long?,
    val fcm_token : String?,
)