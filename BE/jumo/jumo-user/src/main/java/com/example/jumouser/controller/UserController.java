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

import javax.swing.text.html.Option;
import java.util.Optional;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserFactory userFactory;

    @GetMapping("/login")
    public User login(@ModelAttribute("loginRequestDto") LoginRequestDto requestDto){

        UserInfoDto userInfoDto = userFactory.loginSelector(requestDto.getType()).getUserInfo(requestDto);
        Optional<User> user = userFactory.loginSelector(requestDto.getType()).checkUser(userInfoDto);
        if(user.isPresent()){
            return user.get();
        }else{
            return null;
        }
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
