package com.team.parking.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.team.parking.domain.usecase.DeleteShareLotUseCase
import com.team.parking.domain.usecase.GetShareLotListUseCase
import com.team.parking.domain.usecase.PostShareLotUseCase

class ShareParkingLotViewModelFactory (
    private val app : Application, private val postShareLotUseCase: PostShareLotUseCase,
    private val getShareLotListUseCase: GetShareLotListUseCase,
    private val deleteShareLotUseCase: DeleteShareLotUseCase
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShareParkingLotViewModel(app, postShareLotUseCase, getShareLotListUseCase, deleteShareLotUseCase) as T
    }
}