package com.team.parking.data.repository.dataSourceImpl

import com.team.parking.data.api.SearchAPIService
import com.team.parking.data.model.map.SearchKeyWordResponse
import com.team.parking.data.repository.dataSource.SearchRemoteDataSource
import retrofit2.Response

class SearchRemoteDataSourceImpl(
    private val searchAPIService: SearchAPIService
) : SearchRemoteDataSource{
    override suspend fun getSearchDatas(
        apiKey: String,
        query: String
    ): Response<SearchKeyWordResponse> {
        return searchAPIService.getSearchKeyword(apiKey,query)
    }

}