package com.team.parking.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.team.parking.domain.usecase.*

class NotificationViewModelFactory(
    private val app: Application,
    private val getNotiListUseCase: GetNotiListUseCase,
    private val putNotiUseCase: PutNotiUseCase,
    private val deleteAllNotiUseCase: DeleteAllNotiUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotificationViewModel(
            app,
            getNotiListUseCase,
            putNotiUseCase,
            deleteAllNotiUseCase
        ) as T
    }
}