package com.team.parking.data.repository.dataSource

import com.team.parking.data.model.map.SearchAddressResponse
import com.team.parking.data.model.map.SearchKeyWordResponse
import retrofit2.Response

interface SearchRemoteDataSource {

    suspend fun getSearchDatas(apiKey : String, query : String) : Response<SearchKeyWordResponse>
    suspend fun getSearchAddress(apiKey: String, query: String) : Response<SearchAddressResponse>
}