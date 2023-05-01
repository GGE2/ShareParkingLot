package com.example.domain.dto.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {
    private String type;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String profile_image;
    private String social_id;
}
