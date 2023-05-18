package com.team.parking.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.team.parking.domain.usecase.*
import com.team.parking.presentation.utils.App

class MyTicketViewModelFactory(
    private val app:Application,
    val getTicketBoughtListUseCase: GetTicketBoughtListUseCase,
    val getTicketSoldListUseCase: GetTicketSoldListUseCase
):ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyTicketViewModel(
            app,
            getTicketBoughtListUseCase, getTicketSoldListUseCase
        ) as T
    }
}