package com.example.notification.service.impl;

import com.example.domain.repo.NotiRepo;
import com.example.notification.service.NotiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotiServiceImpl implements NotiService {
    private final FirebaseMessaging firebaseMessaging;
    private final NotiRepo notiRepo;

    @Value("${Firebase-API-URL}")
    private String API_URL;

    @Value("${Google-AUTH-URL}")
    private String Google_Auth_URL;

    public void sendNotification(Long user_id, String title, String body) throws IOException, FirebaseMessagingException {

        String targetToken = notiRepo.findById(user_id).get().getToken();

        Message message = makeMessage(targetToken, title, body);

        firebaseMessaging.send(message);
    }


    public Message makeMessage(String targetToken, String title, String body) throws JsonProcessingException {

        Notification notification = Notification.builder().setTitle(title).setBody(body).setImage("http://k8d108.p.ssafy.io:8083/static/logo.png").build();
        System.out.println(targetToken);
        Message message = Message.builder()
                .setToken(targetToken)
                .setNotification(notification)
                .build();

        return message;
    }

    private String getAccessToken() throws IOException {
        String firebaseConfigPath = "jumo-google.json";

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of(Google_Auth_URL));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

}
