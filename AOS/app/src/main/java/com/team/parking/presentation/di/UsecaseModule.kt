package com.team.parking.presentation.di


import com.team.parking.domain.repository.MapRepository
import com.team.parking.domain.repository.SearchRepository
import com.team.parking.domain.repository.UserRepository


import com.team.parking.domain.repository.*
import com.team.parking.domain.usecase.*
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
    fun provideGetMapDataUseCase(
        mapRepository: MapRepository
    ): GetMapDataUseCase {
        return GetMapDataUseCase(mapRepository)
    }

    @Singleton
    @Provides
    fun provideGetMapDetailDataUseCase(
        mapRepository: MapRepository
    ): GetMapDetailDataUseCase {
        return GetMapDetailDataUseCase(mapRepository)
    }

    @Singleton
    @Provides
    fun provideGetSearchDataUseCase(
        searchRepository: SearchRepository
    ): GetSearchDataUseCase {
        return GetSearchDataUseCase(searchRepository)
    }

    @Singleton
    @Provides

    fun provideGetUserUsecase(
        userRepository: UserRepository
    ) : GetUserUseCase {
        return GetUserUseCase(userRepository)

    fun provideGetParkingOrderByDistanceUseCase(
        mapRepository: MapRepository
    ): GetParkingOrderByDistanceDataUseCase {
        return GetParkingOrderByDistanceDataUseCase(mapRepository)
    }


    @Singleton
    @Provides
    fun provideGetParkingOrderByPriceUseCase(
        mapRepository: MapRepository
    ): GetParkingOrderByPriceDataUseCase {
        return GetParkingOrderByPriceDataUseCase(mapRepository)
    }

    @Singleton
    @Provides

    fun provideGetSearchAddressUseCase(
        searchRepository: SearchRepository
    ): GetSearchAddressUseCase {
        return GetSearchAddressUseCase(searchRepository)
    }

    @Singleton
    @Provides
    fun providePostShareLotUseCase(
        shareLotRepository: ShareLotRepository
    ): PostShareLotUseCase {
        return PostShareLotUseCase(shareLotRepository)
    }

    @Singleton
    @Provides
    fun provideGetShareLotListUseCase(
        shareLotRepository: ShareLotRepository
    ): GetShareLotListUseCase {
        return GetShareLotListUseCase(shareLotRepository)
    }

    @Singleton
    @Provides
    fun provideGetShareLotDayUseCase(
        shareLotRepository: ShareLotRepository
    ): GetShareLotDayUseCase {
        return GetShareLotDayUseCase(shareLotRepository)
    }

    @Singleton
    @Provides
    fun providePutShareLotDayUseCase(
        shareLotRepository: ShareLotRepository
    ): PutShareLotDayUseCase {
        return PutShareLotDayUseCase(shareLotRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteShareLotUseCase(
        shareLotRepository: ShareLotRepository
    ): DeleteShareLotUseCase {
        return DeleteShareLotUseCase(shareLotRepository)
    }

    @Singleton
    @Provides
    fun provideGetCurrentPointUseCase(
        pointRepository: PointRepository
    ): GetCurrentPointUseCase {
        return GetCurrentPointUseCase(pointRepository)
    }

    @Singleton
    @Provides
    fun providePutChargePointUseCase(
        pointRepository: PointRepository
    ): PutChargePointUseCase {
        return PutChargePointUseCase(pointRepository)

    }

    @Singleton
    @Provides

    fun providePostUserUsecase(
        userRepository: UserRepository
    ) : PostUserUseCase {
        return PostUserUseCase(userRepository)

    fun provideGetEarnedPointUseCase(
        pointRepository: PointRepository
    ): GetEarnedPointUseCase {
        return GetEarnedPointUseCase(pointRepository)

    }

    @Singleton
    @Provides

    fun providePostAuthMessageUseCase(
        userRepository: UserRepository
    ) : PostAuthMessageUseCase {
        return PostAuthMessageUseCase(userRepository)
    }
    @Singleton
    @Provides
    fun providePutFcmTokenUsecase(
        userRepository: UserRepository
    ) : PutFcmTokenUseCase {
        return PutFcmTokenUseCase(userRepository)

    fun provideGetSpentPointUseCase(
        pointRepository: PointRepository
    ): GetSpentPointUseCase {
        return GetSpentPointUseCase(pointRepository)
    }

    @Singleton
    @Provides
    fun provideSetRepCarUseCase(
        carRepository: CarRepository
    ): SetRepCarUseCase {
        return SetRepCarUseCase(carRepository)
    }

    @Singleton
    @Provides
    fun provideGetCarListUseCase(
        carRepository: CarRepository
    ): GetCarListUseCase {
        return GetCarListUseCase(carRepository)
    }

    @Singleton
    @Provides
    fun providePostCarUseCase(
        carRepository: CarRepository
    ): PostCarUseCase {
        return PostCarUseCase(carRepository)
    }

    @Singleton
    @Provides
    fun provideSharedLotUseCase(
        shareLotRepository: ShareLotRepository
    ) : GetSelectedShareLotUseCase{
        return GetSelectedShareLotUseCase(shareLotRepository)
    }

    @Singleton
    @Provides
    fun provideGetTicketAvailableUseCase(
        ticketRepository: TicketRepository
    ) : GetTicketAvailableUseCase{
        return GetTicketAvailableUseCase(ticketRepository)
    }

    @Singleton
    @Provides
    fun providePostPurchaseTicketUseCase(
        ticketRepository: TicketRepository
    ) : PostPurchaseTicketUseCase{
        return PostPurchaseTicketUseCase(ticketRepository)
    }

    @Singleton
    @Provides
    fun provideGetTicketBoughtListUseCase(
        ticketRepository: TicketRepository
    ) : GetTicketBoughtListUseCase{
        return GetTicketBoughtListUseCase(ticketRepository)
    }

    @Singleton
    @Provides
    fun provideGetTicketSoldListUseCase(
        ticketRepository: TicketRepository
    ) : GetTicketSoldListUseCase{
        return GetTicketSoldListUseCase(ticketRepository)
    }

    @Singleton
    @Provides
    fun provideGetTicketDetailUseCase(
        ticketRepository: TicketRepository
    ) : GetTicketDetailUseCase{
        return GetTicketDetailUseCase(ticketRepository)
    }

    @Singleton
    @Provides
    fun providePutTicketBuyConfirmUseCase(
        ticketRepository: TicketRepository
    ) : PutTicketBuyConfirmUseCase{
        return PutTicketBuyConfirmUseCase(ticketRepository)

    }

    @Singleton
    @Provides

    fun provideGetEmailUsecase(
        userRepository: UserRepository
    ) : GetEmailUseCase {
        return GetEmailUseCase(userRepository)

    fun providePutTicketSellConfirmUseCase(
        ticketRepository: TicketRepository
    ) : PutTicketSellConfirmUseCase{
        return PutTicketSellConfirmUseCase(ticketRepository)

    }

    @Singleton
    @Provides

    fun provideGetAuthMessageUsecase(
        userRepository: UserRepository
    ) : GetAuthMessageUseCase {
        return GetAuthMessageUseCase(userRepository)
    }
    @Singleton
    @Provides
    fun providePutProfileImageUsecase(
        userRepository: UserRepository
    ) : PutProfileImageUseCase {
        return PutProfileImageUseCase(userRepository)
    }
    @Singleton
    @Provides
    fun provideGetUserInfoUsecase(
        userRepository: UserRepository
    ) : GetUserInfoUseCase {
        return GetUserInfoUseCase(userRepository)
    }
}

    fun provideSetFavoriteUseCase(
        favoriteRepository: FavoriteRepository
    ) : SetFavoriteUseCase{
        return SetFavoriteUseCase(favoriteRepository)
    }

    @Singleton
    @Provides
    fun provideGetFavoriteListUseCase(
        favoriteRepository: FavoriteRepository
    ) : GetFavoriteListUseCase{
        return GetFavoriteListUseCase(favoriteRepository)
    }
}

