package com.team.parking.domain.usecase

import com.team.parking.data.model.map.SearchAddressResponse
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.SearchRepository

class GetSearchAddressUseCase(
    private val searchRepository: SearchRepository
) {

    suspend fun execute(apiKey:String,query:String) : Resource<SearchAddressResponse> {
        return searchRepository.getSearchAddress(apiKey, query)
    }

}