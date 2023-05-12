package com.team.parking.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import com.team.parking.MainActivity
import com.team.parking.R
import com.team.parking.data.api.UserAPIService
import com.team.parking.data.model.user.LoginResponse
import com.team.parking.data.util.Resource
import com.team.parking.databinding.FragmentLoginBinding
import com.team.parking.presentation.utils.App
import com.team.parking.presentation.viewmodel.UserViewModel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val TAG = "LoginFragment종건"

class LoginFragment : Fragment() {

    private lateinit var fragmentLoginBinding: FragmentLoginBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentLoginBinding = FragmentLoginBinding.bind(view)
        userViewModel = (activity as MainActivity).userViewModel
        fragmentLoginBinding.apply {
            handlers = this@LoginFragment
            lifecycleOwner = this@LoginFragment
            viewModel = userViewModel
        }


        userViewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            when (it.data?.type) {
                "kakao" -> {
                    if (it.data?.user_id == null) {
                        userViewModel._profileImage.postValue(it.data?.profile_img.toString())
                        userViewModel._social_id = it.data?.social_id.toString()
                        userViewModel._type = it.data?.type.toString()
                        userViewModel._loginResponse = MutableLiveData<Resource<LoginResponse>>()
                        requireActivity().runOnUiThread {
                            findNavController().navigate(R.id.action_login_fragment_to_signUpFragment)
                        }
                    } else {
                        userViewModel.login(it)
                        requireActivity().runOnUiThread {
                            findNavController().navigate(R.id.action_loginFragment_to_mapFragment)
                        }
                    }
                }
                "naver" -> {
                    if (it.data?.user_id == null) {
                        userViewModel._profileImage.postValue(it.data?.profile_img.toString())
                        userViewModel._social_id = it.data?.social_id.toString()
                        userViewModel._userName.postValue(it.data?.name.toString())
                        userViewModel._type = it.data?.type.toString()
                        userViewModel._loginResponse = MutableLiveData<Resource<LoginResponse>>()
                        requireActivity().runOnUiThread {
                            findNavController().navigate(R.id.action_login_fragment_to_signUpFragment)
                        }
                    } else {
                        userViewModel.login(it)
                        requireActivity().runOnUiThread {
                            findNavController().navigate(R.id.action_loginFragment_to_mapFragment)
                        }
                    }
                }
                else -> {
                    if (it.data?.user_id == null) {
                        requireActivity().runOnUiThread {
                            Log.d(TAG, "setOnJumoLogin: Bad")
                            Toast.makeText(requireContext(), "${R.string.dialog_login_failed}", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        requireActivity().runOnUiThread {
                            Log.d(TAG, "setOnJumoLogin: Good")
                            userViewModel.login(it)

                            findNavController().navigate(R.id.action_loginFragment_to_mapFragment)
                        }
                    }
                }

            }

        })

        userViewModel.jumo.observe(viewLifecycleOwner, Observer {
            if(!it){
                Toast.makeText(requireContext(),"로그인 정보가 일치하지 않습니다",Toast.LENGTH_SHORT).show()
            }
            userViewModel.jumo= MutableLiveData()
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }


    fun setOnJumoLogin() {
        userViewModel.setOnJumoLogin()
    }

    fun setOnKakaoLogin() {
        // 이메일 로그인 콜백
        val mCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {

                Log.e(TAG, "로그인 실패:콜백 $error")
            } else if (token != null) {
                Log.e(TAG, "로그인 성공:콜백 ${token.accessToken}")
            }
        }

        // 카카오톡 설치 확인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(mainActivity)) {
            // 카카오톡 로그인
            UserApiClient.instance.loginWithKakaoTalk(mainActivity) { token, error ->
                // 로그인 실패 부분
                if (error != null) {
                    Log.e(TAG, "로그인 실패 $error")
                    // 사용자가 취소
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    // 다른 오류
                    else {
                        UserApiClient.instance.loginWithKakaoAccount(
                            mainActivity,
                            callback = mCallback
                        ) // 카카오 이메일 로그인
                    }
                }
                // 로그인 성공 부분
                else if (token != null) {
                    Log.e(TAG, "로그인 성공 ${token.accessToken}")

                    UserApiClient.instance.me { user, error ->
                        CoroutineScope(Dispatchers.IO).launch {
                            userViewModel.setOnKakaoLogin(
                                user!!.id.toString(),
                                token.accessToken
                            )
                        }
                    }
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(
                mainActivity,
                callback = mCallback
            ) // 카카오 이메일 로그인
        }
    }

    fun setOnNaverLogin() {
        var naverToken: String? = ""
        val profileCallback = object : NidProfileCallback<NidProfileResponse> {
            override fun onSuccess(response: NidProfileResponse) {

                val userId = response.profile?.id
                val userName = response.profile?.name
                Log.d(TAG, "onSuccess: $userName")
                val userImage = response.profile?.profileImage
                CoroutineScope(Dispatchers.IO).launch {
                    userViewModel.setOnNaverLogin(userId,userName,userImage)
                }
            }

            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Toast.makeText(
                    requireContext(), "errorCode: ${errorCode}\n" +
                            "errorDescription: ${errorDescription}", Toast.LENGTH_SHORT
                ).show()
            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

        /** OAuthLoginCallback을 authenticate() 메서드 호출 시 파라미터로 전달하거나 NidOAuthLoginButton 객체에 등록하면 인증이 종료되는 것을 확인할 수 있습니다. */
        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                // 네이버 로그인 인증이 성공했을 때 수행할 코드 추가
                naverToken = NaverIdLoginSDK.getAccessToken()
//                var naverRefreshToken = NaverIdLoginSDK.getRefreshToken()
//                var naverExpiresAt = NaverIdLoginSDK.getExpiresAt().toString()
//                var naverTokenType = NaverIdLoginSDK.getTokenType()
//                var naverState = NaverIdLoginSDK.getState().toString()

                //로그인 유저 정보 가져오기
                NidOAuthLogin().callProfileApi(profileCallback)
            }

            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Toast.makeText(
                    requireContext(), "errorCode: ${errorCode}\n" +
                            "errorDescription: ${errorDescription}", Toast.LENGTH_SHORT
                ).show()
            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

        NaverIdLoginSDK.authenticate(requireContext(), oauthLoginCallback)
    }

    fun setOnSignUp() {
        userViewModel._type = "jumo"
        findNavController().navigate(R.id.action_login_fragment_to_signUpFragment)
    }


}