package com.team.parking.presentation.di

import com.team.parking.domain.repository.MapRepository
import com.team.parking.domain.usecase.GetMapDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class UsecaseModule {

    @Singleton
    @Provides
    fun provideGetMapUsecaseDataUseCase(
        mapRepository: MapRepository
    ) : GetMapDataUseCase{
        return GetMapDataUseCase(mapRepository)
    }

}