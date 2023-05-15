package com.team.parking.domain.usecase

import com.kakao.sdk.common.KakaoSdk.type
import com.navercorp.nid.oauth.NidOAuthPreferencesManager.accessToken
import com.team.parking.data.model.user.LoginResponse
import com.team.parking.data.model.user.UserProfileResponse
import com.team.parking.data.util.Resource
import com.team.parking.domain.repository.UserRepository

class GetUserInfoUseCase (
    private val userRepository: UserRepository
        ){
    suspend fun execute(user_id : Long) : Resource<UserProfileResponse> {
        return userRepository.getUserInfo(user_id)
    }
}