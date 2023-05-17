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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
            com.example.domain.entity.Notification saveNoti = notiRepo.save(noti);
            content.setNoti_id(saveNoti.getNoti_id());
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
    public List<GetNotiListResponseDto> getNotiList(Long noti_id) {
        List<com.example.domain.entity.Notification> notification = notiRepo.findAllById(noti_id);
        List<GetNotiListResponseDto> response = notification.stream().filter(e -> e.getStatus() != false).map(e -> new GetNotiListResponseDto(e)).collect(Collectors.toList());
        response.stream().map(e -> {
            e.setTitle(com.example.notification.util.Message.getMessage(e.getType()).getTitle());
            e.setContent(com.example.notification.util.Message.getMessage(e.getType()).getBody());
            return e;
        });

        return response;
    }

    @Override
    public void readNotification(Long noti_id) {
        Optional<com.example.domain.entity.Notification> noti = notiRepo.findById(noti_id);
        if (!noti.isEmpty()) {
            noti.get().setStatus(false);
            notiRepo.save(noti.get());
        }
    }


    public Message makeMessage(String targetToken, String title, String body) throws JsonProcessingException {

        Notification notification = Notification.builder().setTitle(title).setBody(body).build();

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
        Long id = redisTemplate.opsForValue().increment(key);

        return id;
    }

}
