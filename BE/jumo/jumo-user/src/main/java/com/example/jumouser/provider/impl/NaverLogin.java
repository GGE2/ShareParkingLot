package com.example.jumouser.provider.impl;

import com.example.domain.dto.user.LoginRequestDto;
import com.example.domain.dto.user.UserInfoDto;
import com.example.domain.entity.User;
import com.example.domain.repo.UserRepo;
import com.example.jumouser.provider.LoginProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class NaverLogin implements LoginProvider {

    private final UserRepo userRepo;

    private NaverLogin(UserRepo userRepo){
        this.userRepo =userRepo;
    }
    public static NaverLogin getInstance(UserRepo userRepo){
        return new NaverLogin(userRepo);
    }
    @Override
    public UserInfoDto getUserInfo(LoginRequestDto requestDto) {
        return UserInfoDto.builder()
                .social_id(requestDto.getSocial_id())
                .build();
    }


    @Override
    public Optional<User> checkUser(UserInfoDto userInfoDto){

        List<User> user = userRepo.findBySocialId(userInfoDto.getSocial_id());

        if(user.isEmpty()){
            return Optional.of(new User());
        }else{
            return user.stream().findAny();
        }
    }


}
