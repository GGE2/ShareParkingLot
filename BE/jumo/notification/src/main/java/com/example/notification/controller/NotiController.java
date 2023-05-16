package com.example.notification.controller;


import com.example.domain.dto.PushNotiDto;
import com.example.notification.service.NotiService;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/noti")
@RequiredArgsConstructor
public class NotiController {

    private final NotiService notiService;
    @PostMapping("/send")
    public void pushNotification(@RequestBody PushNotiDto pushNotiDto) throws IOException, FirebaseMessagingException {
        notiService.sendNotification(pushNotiDto.getData());
    }
}
