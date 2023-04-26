package com.example.jumouser.provider.impl;

import com.example.domain.dto.UserInfoDto;
import com.example.jumouser.provider.LoginProvider;
import org.springframework.stereotype.Component;

@Component
public class NaverLogin implements LoginProvider {
    @Override
    public UserInfoDto getUserInfo(String accessToken) {
        return null;
    }
}
