package com.example.jumouser.factory;

import com.example.jumouser.provider.LoginProvider;
import com.example.jumouser.provider.impl.KakaoLogin;
import com.example.jumouser.provider.impl.NaverLogin;

public class UserFactory {

    public LoginProvider loginSelector(String type){
        switch (type) {
            case "kakao" :
                return new KakaoLogin();
            case "naver" :
                return new NaverLogin();
            default:
                return null;
        }
    }

}
