package com.example.notification.service.impl;

import com.example.domain.dto.notification.GetNotiListResponseDto;
import com.example.domain.repo.NotiRepo;
import com.example.domain.repo.TokenRepo;
import com.example.notification.service.NotiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotiServiceImpl implements NotiService {
    private final FirebaseMessaging firebaseMessaging;
    private final TokenRepo tokenRepo;
    private final NotiRepo notiRepo;

    private final RedisTemplate<String, Long> redisTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Value("${Firebase-API-URL}")
    private String API_URL;

    @Value("${Google-AUTH-URL}")
    private String Google_Auth_URL;


    public void sendNotification(Map<String, String> data) throws IOException, FirebaseMessagingException {
        String targetToken = tokenRepo.findById(Long.parseLong(data.get("user_id"))).get().getToken();
        int type = Integer.parseInt(data.get("type"));
        GetNotiListResponseDto content = new GetNotiListResponseDto();
        if (type != 0) {
            //db에 저장
            com.example.domain.entity.Notification noti =
                    new com.example.domain.entity.Notification(
                            getNextNotiId(), Long.parseLong(data.get("user_id")),
                            Long.parseLong(data.get("ticket_id")),
                            true, type);
            redisTemplate.opsForHash().put("Notification:" + noti.getNoti_id() + ":" + noti.getUser_id(),
                    "key", noti);

            content.setNoti_id(noti.getNoti_id());
        }
        content.setTitle(com.example.notification.util.Message.getMessage(type).getTitle());
        content.setContent(com.example.notification.util.Message.getMessage(type).getBody());
        content.setUser_id(Long.parseLong(data.get("user_id")));
        content.setType(type);
        content.setTicket_id(Long.parseLong(data.get("ticket_id")));

        String msg = objectMapper.writeValueAsString(content);
        System.out.println(msg);
        Message message = makeMessage(targetToken, com.example.notification.util.Message.getMessage(type).getTitle(),
                msg);

        firebaseMessaging.send(message);
    }

    @Override
    public List<GetNotiListResponseDto> getNotiList(Long user_id) {

        String hashKeyPattern = "Notification:*:" + user_id;

        Set<String> matchingKeys = redisTemplate.keys(hashKeyPattern);

        List<com.example.domain.entity.Notification> notifications = new ArrayList<>();

        for (String hashKey : matchingKeys) {
            com.example.domain.entity.Notification notification = (com.example.domain.entity.Notification) redisTemplate.opsForHash().get(hashKey, "key");
            notifications.add(notification);
            System.out.println(notification.getStatus());
        }

        List<GetNotiListResponseDto> response = notifications.stream().filter(e-> e.getStatus()!=false)
                .filter(e-> e.getType()!=0)
                .map(e->new GetNotiListResponseDto(e)).collect(Collectors.toList());;

        for(GetNotiListResponseDto dto:response){
            dto.setTitle(com.example.notification.util.Message.getMessage(dto.getType()).getTitle());
            dto.setContent(com.example.notification.util.Message.getMessage(dto.getType()).getBody());
        }
        System.out.println("response");
        System.out.println(response);
        return response;
    }

    @Override
    public Boolean readNotification(Long noti_id) {
        System.out.println(noti_id);
        String hashKeyPattern = "Notification:" + noti_id + ":*";
        Set<String> matchingKeys = redisTemplate.keys(hashKeyPattern);
        List<com.example.domain.entity.Notification> notifications = new ArrayList<>();

        for (String hashKey : matchingKeys) {
            com.example.domain.entity.Notification notification = (com.example.domain.entity.Notification) redisTemplate.opsForHash().get(hashKey, "key");
            notifications.add(notification);
        }
        System.out.println(notifications);
        if (!notifications.isEmpty()) {
            com.example.domain.entity.Notification noti = notifications.get(0);
            System.out.println(noti.getUser_id());
            System.out.println(noti.getNoti_id());
            noti.setStatus(false);
            redisTemplate.opsForHash().put("Notification:" + noti.getNoti_id() + ":" + noti.getUser_id(),
                    "key", noti);


            return true;
        }
        return false;
    }

    public Boolean removeAll(Long user_id){
        String hashKeyPattern = "Notification:*:" + user_id;

        Set<String> matchingKeys = redisTemplate.keys(hashKeyPattern);
        System.out.println(user_id);
        List<com.example.domain.entity.Notification> notifications = new ArrayList<>();

        for (String hashKey : matchingKeys) {
            redisTemplate.delete(hashKey);
        }

        return true;
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

    private Long getNextNotiId() {
        String key = "Notification";
        Long id = redisTemplate.opsForValue().increment("noti_id");

        return id;
    }

}
