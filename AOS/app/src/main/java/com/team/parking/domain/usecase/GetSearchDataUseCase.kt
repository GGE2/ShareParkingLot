package com.team.parking.domain.usecase

import com.team.parking.data.model.map.SearchKeyWordResponse
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.MapRepository
import com.team.parking.domain.repository.SearchRepository

class GetSearchDataUseCase(
    private val searchRepository: SearchRepository
) {

    suspend fun execute(apiKey:String,query:String) : Resource<SearchKeyWordResponse>{
        return searchRepository.getSearchDatas(apiKey, query)
    }

}