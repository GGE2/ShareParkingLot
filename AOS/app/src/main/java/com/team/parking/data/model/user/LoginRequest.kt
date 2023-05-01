package com.team.parking.data.model.user


data class LoginRequest(
    val type: String,
    val accessToken : String? =null,
    val email : String? =null,
    val password : String? =null,
    val social_id : String? =null,
    val name : String? = null,
    val profile_img : String?=null
)