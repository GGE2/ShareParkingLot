package com.example.jumouser.provider;

import com.example.domain.dto.user.SignUpRequestDto;
import com.example.domain.dto.user.UserInfoDto;

public interface LoginProvider {
    public UserInfoDto getUserInfo(String accessToken);
//    public void signUp(SignUpRequestDto signUpRequestDto);
}
