package com.team.parking.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.team.parking.data.model.user.LoginResponse
import com.team.parking.data.model.user.PhoneRequest
import com.team.parking.data.model.user.SignUpRequest
import com.team.parking.data.model.user.User
import com.team.parking.data.util.Resource
import com.team.parking.domain.usecase.*
import com.team.parking.presentation.utils.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private val TAG = "userviewmodel종건"

class UserViewModel(
    private val app: Application,
    private val getUserUseCase: GetUserUseCase,
    private val postUserUseCase: PostUserUseCase,
    private val putFcmTokenUseCase: PutFcmTokenUseCase,
    private val postAuthMessageUseCase: PostAuthMessageUseCase,
    private val getAuthMessageUseCase: GetAuthMessageUseCase,
    private val getEmailUseCase: GetEmailUseCase,
    private val putProfileImageUseCase: PutProfileImageUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
) : AndroidViewModel(app) {
    val application = App()


    var _userName: String = ""
    var _type: String = ""
    var _profileImage : MutableLiveData<String> = MutableLiveData()
    val profileImage: LiveData<String> get() = _profileImage
    var _phone: String = ""
    var _code : String = ""
    var _password: MutableLiveData<String> = MutableLiveData()
    val password: LiveData<String> get() = _password
    var _email: MutableLiveData<String> = MutableLiveData()
    val email: LiveData<String> get() = _email
    var _check_email: MutableLiveData<Boolean> = MutableLiveData()
    val check_email: LiveData<Boolean> get() = _check_email
    var _check_phone: Boolean = false
    var _check_password: Boolean = false
    var _login_email: String = ""
    var _login_password: String = ""
    var _social_id: String = ""
    var validTime: MutableLiveData<String> = MutableLiveData()
    val timeLiveData: LiveData<String> get() = validTime
    var changePhone : Boolean =false
    var `try`: Int = 0
    var rep_car : MutableLiveData<String> = MutableLiveData()
    var total_transaction : MutableLiveData<Int> = MutableLiveData()

    private var _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>> get() = _loginResponse

    // 여기까지 login 전
    var _userLiveData: MutableLiveData<User> = MutableLiveData()
    val userLiveData: LiveData<User> get() = _userLiveData
    private var _fcm_token: MutableLiveData<String> = MutableLiveData()
    val fcm_token: LiveData<String> get() = _fcm_token


    fun setOnJumoLogin() = viewModelScope.launch(Dispatchers.IO) {

        getFCMToken { fcm_token ->
            Log.d(TAG, "setOnJumoLogin: ${_login_password}")

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val result = getUserUseCase.execute(
                        type = "jumo",
                        null,
                        email = _login_email,
                        password = _login_password,
                        fcm_token = fcm_token,
                        name = null,
                        profile_img = null,
                        social_id = null
                    )

                    _loginResponse.postValue(result)
                } catch (e: Exception) {
                    _loginResponse.postValue(Resource.Error(e.message.toString()))
                }
            }
        }
    }

    fun setOnKakaoLogin(social_id: String, accessToken: String) =
        viewModelScope.launch(Dispatchers.IO) {
            getFCMToken { fcm_token ->
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val result = getUserUseCase.execute(
                            type = "kakao",
                            accessToken = accessToken,
                            email = null,
                            password = null,
                            fcm_token = fcm_token,
                            name = null,
                            profile_img = null,
                            social_id = social_id
                        )

                        _loginResponse.postValue(result)
                    } catch (e: Exception) {
                        _loginResponse.postValue(Resource.Error(e.message.toString()))
                    }
                }
            }
        }

    fun setOnNaverLogin(social_id: String?, user_name: String?, user_image: String?) =
        viewModelScope.launch(Dispatchers.IO) {
            getFCMToken { fcm_token ->
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val result = getUserUseCase.execute(
                            type = "naver",
                            accessToken = null,
                            email = null,
                            password = null,
                            fcm_token = fcm_token,
                            name = user_name,
                            social_id = social_id,
                            profile_img = user_image,
                        )

                        _loginResponse.postValue(result)
                    } catch (e: Exception) {
                        _loginResponse.postValue(Resource.Error(e.message.toString()))
                    }
                }
            }
        }


    fun updateFcmToken(token: String) {
        _fcm_token.postValue(token)
    }

    fun signReset() {
        _userName = ""
        _phone = ""
        _email = MutableLiveData()
        _type = ""
        _profileImage = MutableLiveData()
        _password = MutableLiveData()
        _social_id = ""
        changePhone = false
        `try` = 0
        _check_email = MutableLiveData()
        _check_phone = false
        _check_password = false
    }

    fun checkEmail(email: String) = viewModelScope.launch(Dispatchers.IO) {

        val result = getEmailUseCase.execute(email)
        if (result.data == true) {
            _check_email.postValue(true)
            Log.d(TAG, "checkEmail: true")
        } else {
            _check_email.postValue(false)
            Log.d(TAG, "checkEmail: false")
        }

    }

    fun signUp() = viewModelScope.launch(Dispatchers.IO) {


        val result = postUserUseCase.execute(
            SignUpRequest(
                _userName, _phone,
                _email.value!!, _type,
                _profileImage.value, _password.value!!,
                _social_id
            )
        )

        signReset()
        _login_email = result.data?.email!!
        _login_password = result.data?.password!!
    }


    private fun getFCMToken(callback: (String?) -> Unit) {
        var token: String? = null

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                callback(null)
            }

            // Get new FCM registration token
            token = task.result

            // Log and toast
            Log.d(TAG, "FCM Token is ${token}")
            callback(token)
        })

    }

    fun login(
        loginResponse: Resource<LoginResponse>
    ) {
        val user: User = User(
            loginResponse.data?.user_id!!,
            loginResponse.data?.phone!!,
            loginResponse.data?.email!!,
            loginResponse.data?.name!!,
            loginResponse.data?.profile_img,
            loginResponse.data.ptHas,
            loginResponse.data.type!!,
            loginResponse.data.social_id!!,
            loginResponse.data.fcm_token,
        )

        _userLiveData.postValue(user)
    }

    fun sendAuthMessage() = viewModelScope.launch (Dispatchers.IO){
        postAuthMessageUseCase.execute(PhoneRequest(_phone))
        changePhone =true
    }

    fun confirmAuthMessage() = viewModelScope.launch(Dispatchers.IO) {
        val result = getAuthMessageUseCase.execute(_phone,_code)
        if(result.data == true){
            _check_phone=true
        }
        else{
            if(++`try` == 3){
                `try` = 0
                changePhone = false
                _check_phone=false
            }
        }
    }
    fun getProfile()= viewModelScope.launch (Dispatchers.IO){
        val result = getUserInfoUseCase.execute(userLiveData.value!!.user_id)

        if(result.data?.car_str.isNullOrBlank()){
            rep_car.postValue("대표 차량 없음")
        }else{

            rep_car.postValue(result.data?.car_str!!)
        }

        total_transaction.postValue(result.data?.total_transaction)
    }

    fun updateProfileImage()=viewModelScope.launch (Dispatchers.IO){
        
    }


}