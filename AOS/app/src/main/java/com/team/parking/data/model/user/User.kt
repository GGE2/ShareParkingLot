package com.team.parking.data.model.user

import java.lang.reflect.Constructor

data class User(
    var user_id : Long,
    var phone : String,
    var email : String,
    var password : String,
    var name : String,
    var fcm_token : String?=null,
    var profile_img : String?=null,
    var pt_has : Int=0,
    var social_id : String,
    var type: String) {

    constructor(user_id: Long,phone: String,email: String,name: String,profile_img: String?,pt_has: Int,type: String,social_id:String,)
            : this(user_id, phone, email, "", name, "", profile_img, pt_has, social_id, type)
}