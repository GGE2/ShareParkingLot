package com.example.jumouser.factory;

import com.example.domain.repo.UserRepo;
import com.example.jumouser.provider.LoginProvider;
import com.example.jumouser.provider.impl.JumoLogin;
import com.example.jumouser.provider.impl.KakaoLogin;
import com.example.jumouser.provider.impl.NaverLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFactory {

    private final UserRepo userRepo;
    public LoginProvider loginSelector(String type){
        switch (type) {
            case "kakao" :
                return KakaoLogin.getInstance(userRepo);
            case "naver" :
                return NaverLogin.getInstance(userRepo);
            default:
                return JumoLogin.getInstance(userRepo);
        }
    }

}
