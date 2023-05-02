package com.team.parking.data.repository

import com.team.parking.data.model.map.MapResponse
import com.team.parking.data.model.map.SearchKeyWordResponse
import com.team.parking.data.repository.dataSource.SearchRemoteDataSource
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.SearchRepository
import retrofit2.Response

class SearchRepositoryImpl(
    private val searchRemoteDataSource: SearchRemoteDataSource
) : SearchRepository{
    override suspend fun getSearchDatas(
        apiKey: String,
        query: String
    ): Resource<SearchKeyWordResponse> {
        return responseToResource(searchRemoteDataSource.getSearchDatas(apiKey, query))
    }

    private fun responseToResource(response: Response<SearchKeyWordResponse>): Resource<SearchKeyWordResponse> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}