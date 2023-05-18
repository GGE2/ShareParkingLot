package com.team.parking.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.team.parking.domain.usecase.*

class TransactionHistoryViewModelFactory (
    private val app : Application,
    private val getEarnedPointUseCase: GetEarnedPointUseCase,
    private val getSpentPointUseCase: GetSpentPointUseCase
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TransactionHistoryViewModel(app, getEarnedPointUseCase, getSpentPointUseCase) as T
    }
}