package com.team.parking.data.api

import com.team.parking.data.model.map.SearchAddressResponse
import com.team.parking.data.model.map.SearchKeyWordResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchAPIService {

    @GET("v2/local/search/keyword.json")
    suspend fun getSearchKeyword(
        @Header("Authorization") key : String,
        @Query("query") query : String
    ) : Response<SearchKeyWordResponse>

    @GET("v2/local/search/address.json")
    suspend fun getSearchAddress(
        @Header("Authorization") key : String,
        @Query("query") query : String
    ) : Response<SearchAddressResponse>
}