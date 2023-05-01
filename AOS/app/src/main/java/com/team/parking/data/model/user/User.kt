package com.team.parking.data.model.user

import java.lang.reflect.Constructor

data class User(
    private var user_id : Long,
    private var phone : String,
    private var email : String,
    private var password : String,
    private var name : String,
    private var fcm_token : String?=null,
    private var profile_img : String?=null,
    private var pt_has : Int=0,
    private var social_id : String,
    private var type: String) {

    constructor(user_id: Long,phone: String,email: String,name: String,profile_img: String?,pt_has: Int,type: String,social_id:String,)
            : this(user_id, phone, email, "", name, "", profile_img, pt_has, social_id, type)
}