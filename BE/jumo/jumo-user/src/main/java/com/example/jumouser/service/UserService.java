package com.example.jumouser.service;

import com.example.domain.dto.user.SignUpRequestDto;
import com.example.domain.dto.user.UserInfoDto;
import com.example.domain.entity.User;

public interface UserService {

    public boolean emailCheck(String email);
    public User signUp(SignUpRequestDto requestDto);

    User checkUser(UserInfoDto userInfoDto);
}
