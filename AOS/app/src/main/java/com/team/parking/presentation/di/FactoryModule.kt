package com.team.parking.presentation.di

import android.app.Application
import com.team.parking.domain.usecase.GetMapDataUseCase
import com.team.parking.presentation.viewmodel.MapViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun provideMapViewModelFactory(
        app:Application,getMapDataUseCase: GetMapDataUseCase
    ):MapViewModelFactory{
        return MapViewModelFactory(app,getMapDataUseCase)
    }


}