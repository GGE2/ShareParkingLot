package com.team.parking.presentation.di

import com.team.parking.data.api.MapAPIService
import com.team.parking.data.repository.MapRepositoryImpl
import com.team.parking.data.repository.dataSource.MapRemoteDatasource
import com.team.parking.data.repository.dataSourceImpl.MapRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideMapRemoteDataSource(
        mapAPIService: MapAPIService
    ):MapRemoteDatasource{
        return MapRemoteDataSourceImpl(mapAPIService)
    }

}