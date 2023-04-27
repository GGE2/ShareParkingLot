package com.example.jumouser.factory;

import com.example.domain.repo.UserRepo;
import com.example.jumouser.provider.LoginProvider;
import com.example.jumouser.provider.impl.JumoLogin;
import com.example.jumouser.provider.impl.KakaoLogin;
import com.example.jumouser.provider.impl.NaverLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {
    public LoginProvider loginSelector(String type){
        switch (type) {
            case "kakao" :
                return new KakaoLogin();
            case "naver" :
                return new NaverLogin();
            default:
                return new JumoLogin();
        }
    }

}
