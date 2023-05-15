package com.team.parking.data.model.user

import com.team.parking.data.util.Resource

data class User(
    var user_id: Long,
    var phone: String,
    var email: String,
    var password: String,
    var name: String,
    var fcm_token: String?=null,
    var profile_img: String?=null,
    var pt_has: Int=0,
    var social_id: String?,

    var type: String,

    ) {

    constructor(
        user_id: Long,
        phone: String,
        email: String,
        name: String,
        profile_img: String?,
        pt_has: Int,
        type: String,
        social_id:String?,
        fcm_token: String?,

    )
            : this(user_id, phone, email, "", name, fcm_token, profile_img, pt_has, social_id, type)

    constructor(loginResponse: Resource<LoginResponse>): this(loginResponse.data?.user_id!!,
        loginResponse.data.phone!!, loginResponse.data.email!!, loginResponse.data.name!!, loginResponse.data.profile_img,
        loginResponse.data.ptHas, loginResponse.data.type!!, loginResponse.data.social_id, loginResponse.data.fcm_token
        )
    constructor(): this(
        0,"","","","",null,null,0,null,""
    )
}