package com.example.jumouser.provider.impl;

import com.example.domain.dto.user.UserInfoDto;
import com.example.domain.entity.User;
import com.example.domain.repo.UserRepo;
import com.example.jumouser.provider.LoginProvider;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component

public class JumoLogin implements LoginProvider  {

    private final UserRepo userRepo;

    private JumoLogin(UserRepo userRepo){
        this.userRepo=userRepo;
    }
    public static JumoLogin getInstance(UserRepo userRepo){

        return new JumoLogin(userRepo);
    }

    @Override
    public UserInfoDto getUserInfo(String email) {
        return UserInfoDto.builder()
                .email(email)
                .build();
    }

    @Override
    public User login() {
        return null;
    }

    @Override
    public User checkUser(UserInfoDto userInfoDto) {
        Optional<User> user = Optional.ofNullable(userRepo.findByEmail(userInfoDto.getEmail()));

        if(user.isEmpty()){
            return null;
        }else{
            return user.get();
        }
    }


}
