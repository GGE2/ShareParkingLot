package com.team.parking.presentation.di

import com.team.parking.BuildConfig
import com.team.parking.data.api.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetModule {

    val BASE_URL = BuildConfig.BASE_URL

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MapRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class SearchRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class UserRetrofit

    @Singleton
    @Provides
    @MapRetrofit
    fun provideMapRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("${BASE_URL}8082/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideMapService(@MapRetrofit mapRetrofit:Retrofit):MapAPIService{
        return mapRetrofit.create(MapAPIService::class.java)
    }

    @Singleton
    @Provides
    @SearchRetrofit
    fun provideSearchRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SEARCH_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideSearchService(@SearchRetrofit searchRetrofit:Retrofit):SearchAPIService{
        return searchRetrofit.create(SearchAPIService::class.java)
    }

    @Singleton
    @Provides
    fun provideShareLotService(@MapRetrofit mapRetrofit:Retrofit):ShareLotAPIService{
        return mapRetrofit.create(ShareLotAPIService::class.java)
    }

    @Singleton
    @Provides
    fun provideCarService(@MapRetrofit mapRetrofit:Retrofit):CarAPIService{
        return mapRetrofit.create(CarAPIService::class.java)
    }

    @Singleton
    @Provides
    fun provideFavoriteService(@MapRetrofit mapRetrofit:Retrofit):FavoriteAPIService{
        return mapRetrofit.create(FavoriteAPIService::class.java)
    }

    @Singleton
    @Provides
    @UserRetrofit
    fun provideUserRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("${BuildConfig.BASE_URL}8081/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

//    @Singleton
//    @Provides
//    fun provideUserService(@UserRetrofit userRetrofit:Retrofit):UserAPIService{
//        return userRetrofit.create(SearchAPIService::class.java)
//    }

    @Singleton
    @Provides
    fun providePointService(@UserRetrofit userRetrofit:Retrofit):PointAPIService{
        return userRetrofit.create(PointAPIService::class.java)
    }

    @Singleton
    @Provides
    fun provideTicketService(@UserRetrofit userRetrofit: Retrofit):TicketAPIService{
        return userRetrofit.create(TicketAPIService::class.java)
    }
}