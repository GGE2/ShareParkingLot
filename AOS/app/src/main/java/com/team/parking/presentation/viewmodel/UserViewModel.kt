package com.team.parking.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.team.parking.data.model.user.LoginResponse
import com.team.parking.data.model.user.User

private val TAG = "userviewmodel종건"

class UserViewModel : ViewModel() {
    var _userName: String = ""
    var _type: String = ""
    var _profileImage: String = ""
    var _phone: String = ""
    var _password: MutableLiveData<String> = MutableLiveData()
    var _email: MutableLiveData<String> = MutableLiveData()
    var _check_email: Boolean = false
    var _check_phone: Boolean = false
    var _check_password: Boolean = false
    var _login_email :String = ""
    var _login_password :String = ""
    var _social_id : String= ""
    // 여기까지 login 전
    var user: User? = null

    fun login(
        loginResponse: LoginResponse
    ) {
        user = User(
            loginResponse.user_id!!,
            loginResponse.phone!!,
            loginResponse.email!!,
            loginResponse.name!!,
            loginResponse.profile_img,
            loginResponse.ptHas, loginResponse.type!!, loginResponse.social_id ?: ""
        )
    }


    fun signReset() {
        _userName = ""
        _phone = ""
        _email = MutableLiveData()
        _type = ""
        _profileImage = ""
        _password = MutableLiveData()
        _social_id=""

        _check_email = false
        _check_phone = false
        _check_password = false
    }
}