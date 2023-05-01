package com.example.jumouser.controller;



import com.example.domain.dto.user.LoginRequestDto;
import com.example.domain.dto.user.LoginResponseDto;
import com.example.domain.dto.user.SignUpRequestDto;
import com.example.domain.dto.user.UserInfoDto;
import com.example.jumouser.factory.UserFactory;
import com.example.jumouser.provider.LoginProvider;
import com.example.jumouser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.domain.entity.User;

import javax.swing.text.html.Option;
import java.util.Optional;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserFactory userFactory;

    @GetMapping("/login")
    public LoginResponseDto login(@ModelAttribute("loginRequestDto") LoginRequestDto requestDto){
        System.out.println(requestDto.toString());
        UserInfoDto userInfoDto = userFactory.loginSelector(requestDto.getType()).getUserInfo(requestDto);
        Optional<User> user = userFactory.loginSelector(requestDto.getType()).checkUser(userInfoDto);
        LoginResponseDto responseDto = LoginResponseDto.builder()
                .user_id(user.get().getUserId())
                .name(user.get().getName())
                .email(user.get().getEmail())
                .phone(user.get().getPhone())
                .profile_img(user.get().getProfileImg())
                .ptHas(user.get().getPtHas())
                .type(user.get().getType())
                .social_id(user.get().getSocialId())
                .build();
        System.out.println(responseDto.toString());
        return responseDto;
    }

    @PostMapping("/sign")
    public User signUp(@RequestBody SignUpRequestDto signUpRequestDto){

        Optional<User> user = userService.signUp(signUpRequestDto);
        return user.get();
    }

    @GetMapping("/email")
    public boolean emailCheck(@RequestParam String email){
        return userService.emailCheck(email);
    }
}
