package com.team.parking.data.model.user

data class SignUpRequest(
    val name : String,
    val phone : String,
    val email : String,
    val type : String,
    val profile_image : String?,
    val password : String,
    val social_id : String,
)