package com.team.parking.data.model.user

data class LoginResponse (val user_id: Long? = null,
                          val name: String? = "",
                          val type: String? = "",

                          val phone: String? = "",
                          val email: String? = "",

                          val profile_img: String? = "",
                          val ptHas: Int = 0,
                          val social_id : String?="",
                          val fcm_token : String?="",
                          ) {

    override fun toString(): String {
        return "user_id = ${user_id}," +
                "name = ${name}," +
                "type = ${type}," +
                "phone = ${phone}," +
                "email = $String," +
                "profile_img = $profile_img" +
                "ptHas = $ptHas"
    }
}