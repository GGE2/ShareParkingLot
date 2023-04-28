package com.example.domain.entity;


import javax.persistence.*;
import com.example.domain.dto.user.SignUpRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id()
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    private String name;
    private String type;
    @Column(name="social_id")
    private String socialId;
    private String phone;
    private String email;
    private String password;
    @Column(name="fcm_token")
    private String fcmToken;
    @Column(name="profile_img")
    private String profileImg;
    @Column(name="pt_has")
    private int ptHas;

    public User(SignUpRequestDto requestDto){
        this.email = requestDto.getEmail();
        this.name = requestDto.getName();
        this.type = requestDto.getType();
        this.phone = requestDto.getPhone();
        this.profileImg = requestDto.getProfile_image();
        this.password=requestDto.getPassword();
        this.socialId=requestDto.getSocial_id();
    }

    @OneToMany(mappedBy = "car_id", cascade = CascadeType.ALL)
    private List<CarInfo> carInfoList = new ArrayList<>();

    @OneToMany(mappedBy = "ticket_id", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    @OneToMany(mappedBy = "credit_id")
    private List<Transaction> transactions = new ArrayList<>();
}
