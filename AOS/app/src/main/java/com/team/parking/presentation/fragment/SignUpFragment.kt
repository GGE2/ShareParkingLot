package com.team.parking.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.team.parking.MainActivity
import com.team.parking.R
import com.team.parking.databinding.FragmentSignUpBinding
import com.team.parking.presentation.viewmodel.UserViewModel
import java.util.regex.Matcher
import java.util.regex.Pattern


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

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // 뒤로가기 눌렀을 때 동작할 코드
                Log.i(TAG, "handleOnBackPressed: ")
                userViewModel.signReset()
                findNavController().popBackStack()
            }
        })

        Log.i(TAG, "onViewCreated: ${userViewModel._email}")
        fragmentSignUpBinding.nameText.visibility=View.VISIBLE
        fragmentSignUpBinding.nameInput.visibility=View.VISIBLE

        userViewModel._userName.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrBlank()){
                fragmentSignUpBinding.emailText.visibility = View.VISIBLE
                fragmentSignUpBinding.emailInput.visibility = View.VISIBLE
                fragmentSignUpBinding.emailDesc.visibility= View.VISIBLE
            }else{
                fragmentSignUpBinding.emailText.visibility=View.INVISIBLE
                fragmentSignUpBinding.emailInput.visibility=View.INVISIBLE
                fragmentSignUpBinding.emailDesc.visibility=View.INVISIBLE
                fragmentSignUpBinding.passwordText.visibility=View.INVISIBLE
                fragmentSignUpBinding.passwordInput.visibility=View.INVISIBLE
                fragmentSignUpBinding.passwordDesc.visibility=View.INVISIBLE
                fragmentSignUpBinding.certificationNumberText.visibility = View.INVISIBLE
                fragmentSignUpBinding.certificationNumberInput.visibility = View.INVISIBLE
                fragmentSignUpBinding.signUpBtn.visibility = View.INVISIBLE
                fragmentSignUpBinding.messageBtn.visibility = View.INVISIBLE
                fragmentSignUpBinding.callText.visibility=View.INVISIBLE
                fragmentSignUpBinding.callInput.visibility=View.INVISIBLE
                fragmentSignUpBinding.remainTimeText.visibility=View.INVISIBLE
                fragmentSignUpBinding.remainTime.visibility=View.INVISIBLE
            }
        })



        userViewModel._email.observe(viewLifecycleOwner, Observer {
            Log.i(TAG, "onViewCreated: $it")
            if(!it.isNullOrBlank()) {
                checkEmail(it)
//                fragmentSignUpBinding.passwordText.visibility = View.VISIBLE
//                fragmentSignUpBinding.passwordInput.visibility = View.VISIBLE
//                fragmentSignUpBinding.passwordDesc.visibility = View.VISIBLE
            }else{

//                fragmentSignUpBinding.passwordText.visibility=View.INVISIBLE
//                fragmentSignUpBinding.passwordInput.visibility=View.INVISIBLE
//                fragmentSignUpBinding.passwordDesc.visibility=View.INVISIBLE
//                fragmentSignUpBinding.certificationNumberText.visibility = View.INVISIBLE
//                fragmentSignUpBinding.certificationNumberInput.visibility = View.INVISIBLE
//                fragmentSignUpBinding.signUpBtn.visibility = View.INVISIBLE
//                fragmentSignUpBinding.messageBtn.visibility = View.INVISIBLE
//                fragmentSignUpBinding.callText.visibility=View.INVISIBLE
//                fragmentSignUpBinding.callInput.visibility=View.INVISIBLE
//                fragmentSignUpBinding.remainTimeText.visibility=View.INVISIBLE
//                fragmentSignUpBinding.remainTime.visibility=View.INVISIBLE
            }
        })
        userViewModel._check_email.observe(viewLifecycleOwner, Observer {
            if(!it){
                fragmentSignUpBinding.emailInput.error =
                    resources.getString(R.string.dialog_email_duplicated)
                fragmentSignUpBinding.passwordText.visibility =View.INVISIBLE
                fragmentSignUpBinding.passwordInput.visibility= View.INVISIBLE
                fragmentSignUpBinding.certificationNumberText.visibility = View.INVISIBLE
                fragmentSignUpBinding.certificationNumberInput.visibility = View.INVISIBLE
                fragmentSignUpBinding.signUpBtn.visibility = View.INVISIBLE
                fragmentSignUpBinding.messageBtn.visibility = View.INVISIBLE
                fragmentSignUpBinding.passwordDesc.visibility=View.INVISIBLE
                fragmentSignUpBinding.callText.visibility=View.INVISIBLE
                fragmentSignUpBinding.callInput.visibility=View.INVISIBLE
                fragmentSignUpBinding.remainTimeText.visibility=View.INVISIBLE
                fragmentSignUpBinding.remainTime.visibility=View.INVISIBLE
            }
            else if(it==null){

            }
            else{
                fragmentSignUpBinding.passwordText.visibility =View.VISIBLE
                fragmentSignUpBinding.passwordInput.visibility= View.VISIBLE
            }
        })
        userViewModel._password.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrBlank()) {
                checkPassword(it)
                fragmentSignUpBinding.callText.visibility = View.VISIBLE
                fragmentSignUpBinding.callInput.visibility = View.VISIBLE
                if(userViewModel._phone.value?.length == 11){
                    fragmentSignUpBinding.messageBtn.visibility = View.VISIBLE
                }

            }else{
                fragmentSignUpBinding.messageBtn.visibility = View.INVISIBLE
                fragmentSignUpBinding.callText.visibility=View.INVISIBLE
                fragmentSignUpBinding.callInput.visibility=View.INVISIBLE
            }
        })

        userViewModel._phone.observe(viewLifecycleOwner, Observer {

            if(it.length == 11){
                fragmentSignUpBinding.messageBtn.visibility = View.VISIBLE
            }else{
                fragmentSignUpBinding.messageBtn.visibility = View.INVISIBLE
            }
        })

        userViewModel._changePhone.observe(viewLifecycleOwner, Observer {
            if(it){
                fragmentSignUpBinding.certificationNumberText.visibility = View.INVISIBLE
                fragmentSignUpBinding.certificationNumberInput.visibility = View.INVISIBLE
                fragmentSignUpBinding.signUpBtn.visibility = View.INVISIBLE

            }else{
                fragmentSignUpBinding.certificationNumberText.visibility = View.VISIBLE
                fragmentSignUpBinding.certificationNumberInput.visibility = View.VISIBLE
            }
        })




        userViewModel._check_password.observe(viewLifecycleOwner,Observer{
            if(!it){
                fragmentSignUpBinding.callText.visibility=View.INVISIBLE
                fragmentSignUpBinding.callInput.visibility=View.INVISIBLE
                fragmentSignUpBinding.messageBtn.visibility=View.INVISIBLE
                fragmentSignUpBinding.remainTimeText.visibility=View.INVISIBLE
                fragmentSignUpBinding.remainTime.visibility=View.INVISIBLE
                fragmentSignUpBinding.certificationNumberText.visibility = View.INVISIBLE
                fragmentSignUpBinding.certificationNumberInput.visibility = View.INVISIBLE
                fragmentSignUpBinding.certificationBtn.visibility = View.INVISIBLE
                fragmentSignUpBinding.signUpBtn.visibility = View.INVISIBLE
            }else{
                fragmentSignUpBinding.callText.visibility=View.VISIBLE
                fragmentSignUpBinding.callInput.visibility=View.VISIBLE
            }
        })
        fragmentSignUpBinding.messageBtn.setOnClickListener(){
//            userViewModel.sendAuthMessage()
            userViewModel._check_phone.postValue(true)
            fragmentSignUpBinding.remainTime.visibility = View.VISIBLE
            fragmentSignUpBinding.remainTimeText.visibility = View.VISIBLE
            fragmentSignUpBinding.certificationNumberText.visibility = View.VISIBLE
            fragmentSignUpBinding.certificationNumberInput.visibility = View.VISIBLE
            fragmentSignUpBinding.certificationBtn.visibility = View.VISIBLE
        }

        userViewModel.check_phone.observe(viewLifecycleOwner, Observer {
            if(it){
                fragmentSignUpBinding.signUpBtn.visibility = View.VISIBLE
            }
        })
        fragmentSignUpBinding.certificationBtn.setOnClickListener {
            userViewModel.confirmAuthMessage()

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
        val p: Pattern = Pattern.compile("^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$")
        val m: Matcher = p.matcher(email)

        if (!m.matches()) {
            fragmentSignUpBinding.emailInput.error = "이메일 형식으로 입력하세요"
            
            Log.d(TAG, "checkEmail: $email")
            return
        } else {
            // Email format is valid, proceed with further actions
        }


        userViewModel.checkEmail(email)
    }

    fun checkPassword(password: String) {

        val passwordPattern =
            "^(?=.*[a-zA-Z])(?=.*[!@#\$%\\^&\\*\\.])(?=.*[0-9]).{8,20}\$".toRegex()
        val isPasswordValid = passwordPattern.matches(password)
        Log.d(TAG, "checkPassword: $isPasswordValid")
        if (!isPasswordValid) {
            fragmentSignUpBinding.passwordInput.error =
                "${resources.getString(R.string.dialog_password_invalid)}"
            userViewModel._check_password.postValue(false)
        } else {
            userViewModel._check_password.postValue(true)
        }
    }

    fun signUp() {

        if (userViewModel._userName.equals("")) {
            fragmentSignUpBinding.nameInput.error =
                "${resources.getString(R.string.dialog_name_blank)}"
            return
        } else if (!userViewModel.check_password.value!!) {
            fragmentSignUpBinding.passwordInput.error =
                "${resources.getString(R.string.dialog_password_fail)}"
            return
        }

        if (userViewModel.check_email.value == false) {
            return
        }
        if (!userViewModel.check_phone.value!!) {
            Log.d(TAG, "signUp: check_phone_false")
            userViewModel._check_phone.postValue(true)
            return
        }
        Log.d(TAG, "signUp: asdaqwaqfq")
        userViewModel.signUp()

        findNavController().navigate(R.id.action_signUpFragment_to_login_fragment)
    }

    fun checkPhone() {
        Log.d(TAG,"asd")
//        userViewModel.sendAuthMessage()
    }

}