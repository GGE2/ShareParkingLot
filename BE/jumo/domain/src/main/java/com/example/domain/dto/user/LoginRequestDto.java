package com.example.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    private String type;
    private String accessToken;
    private String email;
    private String password;
    private String social_id;
    private String profile_img;
    private String name;

    public String toString(LoginRequestDto requestDto){
        return "type : " +type + " accessToken : " + accessToken +" email : " + email + " password : "  + password + " social_id : " + social_id+ " profile_img : " + profile_img + "name : " + name;
    }
}
