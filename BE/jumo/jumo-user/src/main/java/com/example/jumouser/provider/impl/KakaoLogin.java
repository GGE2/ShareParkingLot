package com.example.jumouser.provider.impl;


import com.example.domainuser.dto.UserInfoDto;
import com.example.jumouser.provider.LoginProvider;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

public class KakaoLogin implements LoginProvider {

    @Value("${KAKAO-KEY}")
    String key;

    @Value("${REDIRECT.URI}")
    String redirect;


    @Override
    public UserInfoDto getUserInfo(String accessToken) {
        String reqURL = "https://kapi.kakao.com";
        try {
            WebClient webClient = WebClient.create(reqURL);
            ResponseEntity<String> response = webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/v2/user/me").build())
                    .header("Authorization", "Bearer "+accessToken)
                    .retrieve().toEntity(String.class).block();
            ObjectMapper objMapper = new ObjectMapper();
            Map<String,Object> obj = objMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>(){});

            String kakao_id = Long.toString((Long)obj.get("id"));
            Map<String,Object> account = (Map<String, Object>) obj.get("kakao_account");
            Map<String,Object> profile = (Map<String, Object>) account.get("profile");
            String image = (String)profile.get("profile_image_url");
            System.out.println("obj");
            System.out.println(obj);
            System.out.println("account");
            System.out.println(account);
            UserInfoDto userInfo = UserInfoDto.builder()
                    .type("kakao")
                    .profile_image(image)
                    .build();

            return userInfo;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}