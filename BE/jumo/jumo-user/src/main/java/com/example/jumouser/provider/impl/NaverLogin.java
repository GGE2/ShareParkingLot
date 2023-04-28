package com.example.jumouser.provider.impl;

import com.example.domain.dto.user.UserInfoDto;
import com.example.domain.entity.User;
import com.example.domain.repo.UserRepo;
import com.example.jumouser.provider.LoginProvider;
import org.springframework.stereotype.Component;

@Component
public class NaverLogin implements LoginProvider {

    private UserRepo userRepo;

    private NaverLogin(UserRepo userRepo){
        this.userRepo =userRepo;
    }
    public static NaverLogin getInstance(UserRepo userRepo){
        return new NaverLogin(userRepo);
    }
    @Override
    public UserInfoDto getUserInfo(String accessToken) {
        return null;
    }

    @Override
    public User login() {
        return null;
    }

    @Override
    public User checkUser(UserInfoDto userInfoDto) {
        return null;
    }


}
