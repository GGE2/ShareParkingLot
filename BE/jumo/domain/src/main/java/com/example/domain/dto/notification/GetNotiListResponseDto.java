package com.example.domain.dto.notification;

import com.example.domain.entity.Notification;
import lombok.Data;

@Data
public class GetNotiListResponseDto {
    private Long noti_id;
    private Long user_id;
    private Long ticket_id;
    private String title;
    private String body;
//    private String parking_region;
    private int type;
//    private String address;

    public GetNotiListResponseDto(Notification notification) {
        this.noti_id = notification.getNoti_id();
        this.user_id = notification.getUser_id();
        this.ticket_id = notification.getTicket_id();
        this.type = notification.getType();
    }


}
