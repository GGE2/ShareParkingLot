package com.example.jumouser.service;

import com.example.domain.dto.user.SignUpRequestDto;
import com.example.domain.entity.User;

public interface UserService {

    boolean emailCheck(String email);
    User signUp(SignUpRequestDto requestDto);

}
