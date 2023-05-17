package com.example.domain.dto.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileResponseDto {
    private String user_id;
    private String name;
    private String profile_img;
    private String email;
    private String car_str;
    private int pt_has;
    private int total_transaction;


    public String toString(){
        return "user_id : " + user_id + "profile+ : " + profile_img;
    }
}