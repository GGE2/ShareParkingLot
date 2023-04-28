package com.example.jumouser.service;

import com.example.domain.dto.user.SignUpRequestDto;
import com.example.domain.entity.User;

import java.util.Optional;

public interface UserService {

    public boolean emailCheck(String email);
    public Optional<User> signUp(SignUpRequestDto requestDto);

}
