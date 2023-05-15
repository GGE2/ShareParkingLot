package com.team.parking.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.team.parking.domain.usecase.*

class PurchaseTicketViewModelFactory (
    private val app : Application,
    private val getTicketAvailableUseCase: GetTicketAvailableUseCase,
    private val postPurchaseTicketUseCase: PostPurchaseTicketUseCase
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PurchaseTicketViewModel(app, getTicketAvailableUseCase, postPurchaseTicketUseCase) as T
    }
}