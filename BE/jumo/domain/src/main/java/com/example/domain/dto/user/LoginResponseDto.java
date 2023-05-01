package com.example.domain.dto.user;

import javax.persistence.Column;

public class LoginResponseDto {
    private Long user_id;
    private String name;
    private String type;

    private String phone;
    private String email;

    private String profile_img;
    private int ptHas;
}
