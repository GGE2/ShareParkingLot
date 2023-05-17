package com.example.domain.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoDto {
    private String type;
    private String email;
    private String profile_image;

    private String social_id;
    private String password;

    private String name;
    private String profile_img;
}
