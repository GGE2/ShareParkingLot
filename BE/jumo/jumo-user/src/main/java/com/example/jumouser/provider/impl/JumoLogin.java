package com.example.jumouser.provider.impl;

import com.example.domain.dto.user.UserInfoDto;
import com.example.domain.repo.UserRepo;
import com.example.jumouser.provider.LoginProvider;
import org.springframework.stereotype.Component;

@Component

public class JumoLogin implements LoginProvider  {

    @Override
    public UserInfoDto getUserInfo(String accessToken) {
        return null;
    }


}
