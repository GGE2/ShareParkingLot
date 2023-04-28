package com.example.jumouser.service.impl;

import com.example.domain.dto.user.SignUpRequestDto;
import com.example.domain.entity.User;
import com.example.domain.repo.UserRepo;
import com.example.jumouser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    public boolean emailCheck(String email){
        List<User> user = userRepo.findByEmail(email);
        if(user.isEmpty()){
            return true;
        }
        return false;
    }


    public Optional<User> signUp(SignUpRequestDto requestDto){
        User user = new User(requestDto);
        userRepo.save(user);
        List<User> sign_user = userRepo.findByEmail(requestDto.getEmail()); // 수정할 것
        return sign_user.stream().findAny();
    }
}
