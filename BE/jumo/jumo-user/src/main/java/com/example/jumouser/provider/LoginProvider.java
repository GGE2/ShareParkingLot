package com.example.jumouser.provider;

import com.example.domain.dto.user.UserInfoDto;
import com.example.domain.entity.User;

public interface LoginProvider {
    UserInfoDto getUserInfo(String accessToken);
//    public void signUp(SignUpRequestDto signUpRequestDto);
User login();

    User checkUser(UserInfoDto userInfoDto);
}
