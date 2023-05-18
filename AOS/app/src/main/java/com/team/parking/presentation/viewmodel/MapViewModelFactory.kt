package com.team.parking.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.team.parking.domain.usecase.*
import com.team.parking.presentation.utils.App

class MapViewModelFactory(
    private val app:Application,
    val getMapDataUseCase: GetMapDataUseCase,
    val getMapDetailDataUseCase: GetMapDetailDataUseCase,
    val getMapOrderByDistanceDataUseCase: GetParkingOrderByDistanceDataUseCase,
    val getMapOrderByPriceDataUseCase: GetParkingOrderByPriceDataUseCase,
    val selectedShareLotUseCase: GetSelectedShareLotUseCase
):ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MapViewModel(
            app,
            getMapDataUseCase,
            getMapDetailDataUseCase,
            getMapOrderByDistanceDataUseCase,
            getMapOrderByPriceDataUseCase,
            selectedShareLotUseCase
        ) as T
    }
}