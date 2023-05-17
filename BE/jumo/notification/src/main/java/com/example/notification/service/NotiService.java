package com.example.notification.service;


import com.example.domain.dto.notification.GetNotiListResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public interface NotiService {
    public void sendNotification(Map<String,String> data) throws IOException, FirebaseMessagingException;
    public List<GetNotiListResponseDto> getNotiList(Long user_id);

    public void readNotification(Long noti_id);
}
