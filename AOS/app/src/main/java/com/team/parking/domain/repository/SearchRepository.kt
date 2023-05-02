package com.team.parking.domain.repository

import com.team.parking.data.model.map.SearchKeyWordResponse
import com.team.parking.data.util.Resource

interface SearchRepository {
    suspend fun getSearchDatas(apiKey:String,query:String) : Resource<SearchKeyWordResponse>
}