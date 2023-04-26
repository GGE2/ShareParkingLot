package com.example.jumouser.provider;

import com.example.domainuser.dto.UserInfoDto;

public interface LoginProvider {
    public UserInfoDto getUserInfo(String accessToken);
}
