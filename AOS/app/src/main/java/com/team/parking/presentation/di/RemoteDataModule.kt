package com.team.parking.presentation.di

import com.team.parking.data.api.MapAPIService
import com.team.parking.data.api.SearchAPIService
import com.team.parking.data.api.ShareLotAPIService
import com.team.parking.data.repository.MapRepositoryImpl
import com.team.parking.data.repository.SearchRepositoryImpl
import com.team.parking.data.repository.dataSource.MapRemoteDatasource
import com.team.parking.data.repository.dataSource.SearchRemoteDataSource
import com.team.parking.data.repository.dataSource.ShareLotRemoteDatasource
import com.team.parking.data.repository.dataSourceImpl.MapRemoteDataSourceImpl
import com.team.parking.data.repository.dataSourceImpl.SearchRemoteDataSourceImpl
import com.team.parking.data.repository.dataSourceImpl.ShareLotRemoteDataSourceImpl
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
    ):MapRemoteDataSource{
        return MapRemoteDataSourceImpl(mapAPIService)
    }

    @Singleton
    @Provides
    fun provideSearchRemoteDataSource(
        searchAPIService: SearchAPIService
    ) : SearchRemoteDataSource{
        return SearchRemoteDataSourceImpl(searchAPIService)
    }

    @Singleton
    @Provides
    fun provideShareLotRemoteDataSource(
        shareLotAPIService: ShareLotAPIService
    ) : ShareLotRemoteDatasource{
        return ShareLotRemoteDataSourceImpl(shareLotAPIService)
    }

}