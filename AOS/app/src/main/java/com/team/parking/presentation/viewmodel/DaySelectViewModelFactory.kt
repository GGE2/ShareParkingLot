package com.team.parking.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.team.parking.domain.usecase.GetShareLotDayUseCase
import com.team.parking.domain.usecase.GetShareLotListUseCase
import com.team.parking.domain.usecase.PostShareLotUseCase
import com.team.parking.domain.usecase.PutShareLotDayUseCase

class DaySelectViewModelFactory (
    private val app : Application,
    private val getShareLotDayUseCase: GetShareLotDayUseCase,
    private val putShareLotDayUseCase: PutShareLotDayUseCase
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DaySelectViewModel(app, getShareLotDayUseCase, putShareLotDayUseCase) as T
    }
}