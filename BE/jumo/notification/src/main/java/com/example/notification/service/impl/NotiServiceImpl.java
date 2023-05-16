package com.example.notification.service.impl;

import com.example.domain.repo.NotiRepo;
import com.example.domain.repo.TokenRepo;
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

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotiServiceImpl implements NotiService {
    private final FirebaseMessaging firebaseMessaging;
    private final TokenRepo tokenRepo;
    private final NotiRepo notiRepo;
    @Value("${Firebase-API-URL}")
    private String API_URL;

    @Value("${Google-AUTH-URL}")
    private String Google_Auth_URL;



    public void sendNotification(Map<String,String> data) throws IOException, FirebaseMessagingException {
        String targetToken = tokenRepo.findById(Long.parseLong(data.get("user_id"))).get().getToken();
        int type = Integer.parseInt(data.get("type"));
        if(type != 0){
            //db에 저장
            String composite_key = data.get("user_id") + ":" + data.get("ticket_id");
            com.example.domain.entity.Notification noti =
                    new com.example.domain.entity.Notification(composite_key, true, type);
            notiRepo.save(noti);
        }else{

        }

        Message message = makeMessage(targetToken, com.example.notification.util.Message.getMessage(type).getTitle(),
                com.example.notification.util.Message.getMessage(type).getBody());

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

    public String getAccessToken() throws IOException {
        String firebaseConfigPath = "jumo-google.json";

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of(Google_Auth_URL));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

    public void readNoti(){

    }


}
