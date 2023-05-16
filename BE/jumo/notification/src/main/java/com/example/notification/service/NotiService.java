package com.example.notification.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

import java.io.IOException;
import java.util.Map;


public interface NotiService {
    public void sendNotification(Map<String,String> data) throws IOException, FirebaseMessagingException;
    public Message makeMessage(String targetToken, String title, String body) throws JsonProcessingException;
}
