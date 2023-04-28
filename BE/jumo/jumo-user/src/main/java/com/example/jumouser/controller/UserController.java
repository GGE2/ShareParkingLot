package com.example.jumouser.controller;


import com.example.domain.dto.user.LoginRequestDto;
import com.example.domain.dto.user.SignUpRequestDto;
import com.example.domain.dto.user.UserInfoDto;
import com.example.domain.entity.User;
import com.example.jumouser.factory.UserFactory;
import com.example.jumouser.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserFactory userFactory;

    @ApiOperation(value = "로그인", notes = "카카오, 네이버의 소셜")
    @GetMapping("/login")
    public User login(@ModelAttribute("loginRequestDto") LoginRequestDto requestDto){

        UserInfoDto userInfoDto = userFactory.loginSelector(requestDto.getType()).getUserInfo(requestDto.getAccessToken());
        Optional<User> user = userFactory.loginSelector(requestDto.getType()).checkUser(userInfoDto);
        return user.get();
    }
    @ApiOperation(value = "로그인", notes = "회원가입")
    @PostMapping("/sign")
    public User signUp(@RequestBody SignUpRequestDto signUpRequestDto){

        Optional<User> user = userService.signUp(signUpRequestDto);
        return user.get();
    }

    @ApiOperation(value = "로그인", notes = "email check")
    @GetMapping("/email")
    public boolean emailCheck(@RequestParam String email){
        return userService.emailCheck(email);
    }
}
