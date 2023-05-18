package com.team.parking.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.team.parking.domain.usecase.*

class UserViewModelFactory (
    private val app : Application,
    val getUserUseCase: GetUserUseCase,
    val getEmailUseCase: GetEmailUseCase,
    val postAuthMessageUseCase: PostAuthMessageUseCase,
    val postUserUseCase: PostUserUseCase,
    val putFcmTokenUseCase: PutFcmTokenUseCase,
    val getAuthMessageUseCase: GetAuthMessageUseCase,
    val putProfileImageUseCase: PutProfileImageUseCase,
    val getUserInfoUseCase: GetUserInfoUseCase,
    ) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UserViewModel(app,getUserUseCase,postUserUseCase,putFcmTokenUseCase,postAuthMessageUseCase,
                getAuthMessageUseCase, getEmailUseCase, putProfileImageUseCase, getUserInfoUseCase) as T
        }
}

