package com.example.domain.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash("FcmToken")
public class FcmToken {
    @Id
    private Long user_id;

    private String token;


    public FcmToken(Long user_id, String token){
        this.user_id = user_id;
        this.token = token;
    }
}
