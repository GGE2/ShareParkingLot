package com.example.jumouser.controller;



import com.example.domainuser.dto.LoginRequestDto;
import com.example.domainuser.dto.UserInfoDto;
import com.example.jumouser.factory.UserFactory;
import com.example.jumouser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserFactory userFactory;
    @PostMapping("/login")
    public void login(@RequestBody LoginRequestDto requestDto){
        UserInfoDto userInfoDto = userFactory.loginSelector(requestDto.getType()).getUserInfo(requestDto.getAccessToken());

    }
}
