package com.team.parking.presentation.di


import com.team.parking.data.repository.MapRepositoryImpl
import com.team.parking.data.repository.SearchRepositoryImpl
import com.team.parking.data.repository.UserRepositoryImpl
import com.team.parking.data.repository.dataSource.MapRemoteDataSource
import com.team.parking.data.repository.dataSource.SearchRemoteDataSource
import com.team.parking.data.repository.dataSource.UserRemoteDataSource
import com.team.parking.domain.repository.MapRepository
import com.team.parking.domain.repository.SearchRepository
import com.team.parking.domain.repository.UserRepository

import com.team.parking.data.repository.*
import com.team.parking.data.repository.dataSource.*
import com.team.parking.domain.repository.*

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
    fun provideUserRepository(
        userRemoteDataSource: UserRemoteDataSource
    ) : UserRepository {
        return UserRepositoryImpl(userRemoteDataSource)

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

    @Singleton
    @Provides
    fun provideCarRepository(
        carRemoteDataSource: CarRemoteDataSource
    ) : CarRepository{
        return CarRepositoryImpl(carRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideTicketRepository(
        ticketRemoteDataSource: TicketRemoteDataSource
    ) : TicketRepository{
        return TicketRepositoryImpl(ticketRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideFavoriteRepositoryy(
        favoriteRemoteDataSource: FavoriteRemoteDataSource
    ) : FavoriteRepository{
        return FavoriteRepositoryImpl(favoriteRemoteDataSource)
    }
}