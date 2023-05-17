package com.example.jumouser.util;

import com.example.domain.dto.notification.PushNotiDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class NotificationUtil {




    public static void sendNotification(Map<String,String> data){
        String reqURL = "http://k8d108.p.ssafy.io:8083/noti/send";
        try {
            WebClient webClient = WebClient.create();
            PushNotiDto pushNotiDto = new PushNotiDto(data);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            webClient.post()
                    .uri(reqURL)
                    .body(BodyInserters.fromValue(pushNotiDto))
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("not authorized");
        }
    }


}
