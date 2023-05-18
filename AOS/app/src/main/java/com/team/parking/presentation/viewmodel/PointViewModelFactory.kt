package com.team.parking.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.team.parking.domain.usecase.GetCurrentPointUseCase
import com.team.parking.domain.usecase.GetMapDataUseCase
import com.team.parking.domain.usecase.GetMapDetailDataUseCase
import com.team.parking.domain.usecase.PutChargePointUseCase
import com.team.parking.presentation.utils.App

class PointViewModelFactory(
    private val app:Application,
    private val getCurrentPointUseCase: GetCurrentPointUseCase,
    private val putChargePointUseCase: PutChargePointUseCase
):ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PointViewModel(
            app,
            getCurrentPointUseCase,
            putChargePointUseCase
        ) as T
    }
}