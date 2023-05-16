package com.example.domain.dto.notification;

import com.example.domain.entity.Notification;
import lombok.Data;

@Data
public class GetNotiListResponseDto {
    private Long user_id;
    private Long ticket_id;
    private String title;
    private String body;
    private String parking_region;
    private int type;
    private String address;

    public GetNotiListResponseDto(Notification notification) {
        String composite_key[] = notification.getUser_ticket_id().split(":");
        this.user_id = Long.parseLong(composite_key[0]);
        this.ticket_id = Long.parseLong(composite_key[1]);
        this.type = notification.getType();
    }


}
