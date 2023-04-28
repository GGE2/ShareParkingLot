package com.example.jumouser.provider;

import com.example.domain.dto.user.UserInfoDto;
import com.example.domain.entity.User;

import java.util.Optional;

public interface LoginProvider {
    UserInfoDto getUserInfo(String accessToken);
//    public void signUp(SignUpRequestDto signUpRequestDto);

    Optional<User> checkUser(UserInfoDto userInfoDto);
}
