package com.team.parking.presentation.di

import com.team.parking.data.repository.MapRepositoryImpl
import com.team.parking.data.repository.dataSource.MapRemoteDatasource
import com.team.parking.domain.repository.MapRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideMapRepository(
        mapRemoteDatasource: MapRemoteDatasource
    ):MapRepository{
        return MapRepositoryImpl(mapRemoteDatasource)
    }

}