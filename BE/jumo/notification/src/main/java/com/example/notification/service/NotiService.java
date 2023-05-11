package com.example.notification.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

import java.io.IOException;


public interface NotiService {
    public void sendNotification(Long user_id, String title, String body) throws IOException, FirebaseMessagingException;
    public Message makeMessage(String targetToken, String title, String body) throws JsonProcessingException;
}
