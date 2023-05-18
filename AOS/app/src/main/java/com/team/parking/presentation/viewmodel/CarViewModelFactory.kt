package com.team.parking.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.team.parking.domain.usecase.*

class CarViewModelFactory (
    private val app : Application,
    private val setRepCarUseCase: SetRepCarUseCase,
    private val getCarListUseCase: GetCarListUseCase,
    private val postCarUseCase: PostCarUseCase
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CarViewModel(app, setRepCarUseCase, getCarListUseCase, postCarUseCase) as T
    }
}