package com.example.jumouser.controller;



import com.example.domain.dto.user.LoginRequestDto;
import com.example.domain.dto.user.SignUpRequestDto;
import com.example.domain.dto.user.UserInfoDto;
import com.example.jumouser.factory.UserFactory;
import com.example.jumouser.provider.LoginProvider;
import com.example.jumouser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.domain.entity.User;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserFactory userFactory;

    @GetMapping("/login")
    public User login(@ModelAttribute("loginRequestDto") LoginRequestDto requestDto){

        UserInfoDto userInfoDto = userFactory.loginSelector(requestDto.getType()).getUserInfo(requestDto.getAccessToken());
        User user = userFactory.loginSelector(requestDto.getType()).checkUser(userInfoDto);
        return user;
    }

    @PostMapping("/sign")
    public User signUp(@RequestBody SignUpRequestDto signUpRequestDto){

        User user = userService.signUp(signUpRequestDto);
        return user;
    }

    @GetMapping("/email")
    public boolean emailCheck(@RequestParam String email){
        return userService.emailCheck(email);
    }
}
