package com.example.domain.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@RedisHash("Notification")
public class Notification {

    public static final Long DEFAULT_TTL = 60*60*24*30L;
    @Id
    private String user_ticket_id;
//    private Boolean buyer;
    private Boolean status;
    private int type;

    @TimeToLive
    private Long expiration = DEFAULT_TTL;

    public Notification(String composite_key,Boolean status, int type){
        this.user_ticket_id = composite_key;
        this.status = status;
        this.type = type;
    }
    public Notification(String composite_key,Boolean status, int type, Long expiration){
        new Notification(composite_key,status,type);
        this.expiration = expiration;
    }

}
