package com.team.parking.data.model.user

data class UserProfileResponse(
    val user_id : Long,
    val name : String,
    val profile_img : String?,
    val email : String,
    val car_str : String?,
    val pt_has : Int,
    val total_transaction : Int,
)