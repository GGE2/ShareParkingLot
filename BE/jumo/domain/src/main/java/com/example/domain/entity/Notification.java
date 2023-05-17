package com.example.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@RedisHash("Notification")
@Setter
public class Notification {

    public static final Long DEFAULT_TTL = 60*60*24*30L;
    @Id
    private Long noti_id;

    private Long user_id;

    private Long ticket_id;
//    private Boolean buyer;
    private Boolean status;
    private int type;

    @TimeToLive
    private Long expiration = DEFAULT_TTL;

    public Notification(Long noti_id, Long user_id,Long ticket_id,Boolean status, int type){
        this.noti_id = noti_id;
        this.user_id = user_id;
        this.ticket_id = ticket_id;
        this.status = status;
        this.type = type;
    }
    public Notification(Long noti_id, Long user_id, Long ticket_id,Boolean status, int type, Long expiration){
        new Notification(noti_id,user_id, ticket_id,status,type);
        this.expiration = expiration;
    }

}
