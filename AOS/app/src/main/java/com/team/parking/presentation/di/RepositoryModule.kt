package com.team.parking.presentation.di

import com.team.parking.data.repository.MapRepositoryImpl
import com.team.parking.data.repository.PointRepositoryImpl
import com.team.parking.data.repository.SearchRepositoryImpl
import com.team.parking.data.repository.ShareLotRepositoryImpl
import com.team.parking.data.repository.dataSource.MapRemoteDataSource
import com.team.parking.data.repository.dataSource.PointRemoteDataSource
import com.team.parking.data.repository.dataSource.SearchRemoteDataSource
import com.team.parking.data.repository.dataSource.ShareLotRemoteDataSource
import com.team.parking.domain.repository.MapRepository
import com.team.parking.domain.repository.PointRepository
import com.team.parking.domain.repository.SearchRepository
import com.team.parking.domain.repository.ShareLotRepository
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
        mapRemoteDataSource: MapRemoteDataSource
    ):MapRepository{
        return MapRepositoryImpl(mapRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideSearchRepository(
        searchRemoteDataSource: SearchRemoteDataSource
    ) : SearchRepository{
        return SearchRepositoryImpl(searchRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideShareLotRepository(
        shareLotRemoteDatasource: ShareLotRemoteDataSource
    ) : ShareLotRepository{
        return ShareLotRepositoryImpl(shareLotRemoteDatasource)
    }

    @Singleton
    @Provides
    fun providePointRepository(
        pointRemoteDataSource: PointRemoteDataSource
    ) : PointRepository{
        return PointRepositoryImpl(pointRemoteDataSource)
    }
}