package com.example.jumouser.provider;

import com.example.domain.dto.UserInfoDto;

public interface LoginProvider {
    public UserInfoDto getUserInfo(String accessToken);
}
