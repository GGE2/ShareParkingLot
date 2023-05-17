package com.example.notification.controller;


import com.example.domain.dto.notification.GetNotiListResponseDto;
import com.example.domain.dto.notification.PushNotiDto;
import com.example.notification.service.NotiService;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/noti")
@RequiredArgsConstructor
public class NotiController {

    private final NotiService notiService;
    @PostMapping("/send")
    public void pushNotification(@RequestBody PushNotiDto pushNotiDto) throws IOException, FirebaseMessagingException {
        notiService.sendNotification(pushNotiDto.getData());
    }

    @GetMapping("/list")
    public List<GetNotiListResponseDto> getNotiList(@RequestParam Long user_id){
        return notiService.getNotiList(user_id);
    }

    @PutMapping("/status")
    public void readNotification(Long user_id, Long ticket_id){

    }
}
