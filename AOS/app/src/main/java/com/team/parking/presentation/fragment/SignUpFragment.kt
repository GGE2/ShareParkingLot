package com.team.parking.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.team.parking.MainActivity
import com.team.parking.R
import com.team.parking.data.api.UserService
import com.team.parking.data.model.user.SignUpRequest
import com.team.parking.databinding.FragmentSignUpBinding
import com.team.parking.presentation.utils.App
import com.team.parking.presentation.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private val TAG = "SignUp종건"

class SignUpFragment : Fragment() {
    private lateinit var fragmentSignUpBinding: FragmentSignUpBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG, "onCreateView: ")
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentSignUpBinding = DataBindingUtil.bind<FragmentSignUpBinding>(view)!!
        userViewModel = (activity as MainActivity).userViewModel
        fragmentSignUpBinding.apply {
            handlers = this@SignUpFragment
            lifecycleOwner = this@SignUpFragment
            viewModel = userViewModel
        }
        Log.i(TAG, "onViewCreated: ${userViewModel._email}")
        userViewModel._email.observe(viewLifecycleOwner, Observer {
            Log.i(TAG, "onViewCreated: $it")
            checkEmail(it)
        })
        userViewModel._password.observe(viewLifecycleOwner, Observer {
            checkPassword(it)
        })

        fragmentSignUpBinding.certificationBtn.setOnClickListener {
            Log.i(TAG, "onViewCreated: ${userViewModel._email}")
        }
        fragmentSignUpBinding.signUpBtn.setOnClickListener {
            Log.i(TAG, "OnViewCreate: $it")
            signUp()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity

    }


    fun checkEmail(email: String) {
        if (email!!.isBlank()) {
            fragmentSignUpBinding.emailInput.error =
                resources.getString(R.string.dialog_email_blank)

        } else {
            CoroutineScope(Dispatchers.IO).launch {
                Log.d(TAG, "checkEmail123: $email")
                val response = App.userRetrofit.create(UserService::class.java).checkEmail(email)
                Log.d(TAG, "checkEmail: ${response.isSuccessful}")
                if (response.isSuccessful) {

                    if (response.body() == true) {
                        requireActivity().runOnUiThread {
                            userViewModel._check_email = true
                        }
                    } else {
                        requireActivity().runOnUiThread {
                            userViewModel._check_email = false
                            fragmentSignUpBinding.emailInput.error =
                                resources.getString(R.string.dialog_email_duplicated)
                        }
                    }
                }
            }
        }


    }

    fun checkPassword(password:String) {

        val passwordPattern = "^(?=.*[a-zA-Z])(?=.*[!@#\$%\\^&\\*\\.])(?=.*[0-9]).{8,20}\$".toRegex()
        val isPasswordValid = passwordPattern.matches(password)
        Log.d(TAG, "checkPassword: $isPasswordValid")
        if(!isPasswordValid){
            fragmentSignUpBinding.passwordInput.error = "${resources.getString(R.string.dialog_password_invalid)}"
            userViewModel._check_password=false
        }else{
            userViewModel._check_password=true
        }
    }

    fun signUp() {

        if (userViewModel._userName.equals("")) {
            fragmentSignUpBinding.nameInput.error = "${resources.getString(R.string.dialog_name_blank)}"
            return
        } else if (!userViewModel._check_password) {
            fragmentSignUpBinding.passwordInput.error = "${resources.getString(R.string.dialog_password_fail)}"
            return
        }

        if (!userViewModel._check_email) {
            fragmentSignUpBinding.emailInput.error="${resources.getString(R.string.dialog_email_invalid)}"
            return
        }
        if (!userViewModel._check_phone) {
            Log.d(TAG, "signUp: check_phone_false")
            userViewModel._check_phone = true
            return
        }

        CoroutineScope(Dispatchers.IO).launch {

            val response = App.userRetrofit.create(UserService::class.java).signUp(
                SignUpRequest(
                    userViewModel._userName, userViewModel._phone,
                    userViewModel._email.value!!, userViewModel._type,
                    userViewModel._profileImage, userViewModel._password.value!!,
                    userViewModel._social_id
                )
            )

            if (response.isSuccessful) {
                Log.d(TAG, "signUp: SignUpSuccess")
                requireActivity().runOnUiThread {
                    userViewModel.signReset()
                    findNavController().navigate(R.id.action_signUpFragment_to_login_fragment)
                }

            }
        }

    }
}