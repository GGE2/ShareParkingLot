package com.example.jumouser.service.impl;

import com.example.domain.dto.user.SignUpRequestDto;
import com.example.domain.dto.user.UserInfoDto;
import com.example.domain.entity.User;
import com.example.domain.repo.UserRepo;
import com.example.jumouser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    public boolean emailCheck(String email){
        Optional<User> user = Optional.ofNullable(userRepo.findByEmail(email));

        return user.isEmpty();
    }


    public User signUp(SignUpRequestDto requestDto){
        User user = new User(requestDto);
        userRepo.save(user);
        User sign_user = userRepo.findByEmail(requestDto.getEmail()); // 수정할 것
        return sign_user;
    }
}
