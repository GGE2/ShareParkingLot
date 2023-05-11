package com.team.parking.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.team.parking.domain.usecase.*

class TicketDetailViewModelFactory (
    private val app : Application,
    private val getTicketDetailUseCase: GetTicketDetailUseCase,
    private val putTicketBuyConfirmUseCase: PutTicketBuyConfirmUseCase,
    private val putTicketSellConfirmUseCase: PutTicketSellConfirmUseCase
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TicketDetailViewModel(app, getTicketDetailUseCase, putTicketBuyConfirmUseCase, putTicketSellConfirmUseCase) as T
    }
}