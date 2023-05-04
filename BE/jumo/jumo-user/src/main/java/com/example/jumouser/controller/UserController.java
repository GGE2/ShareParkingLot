package com.example.jumouser.controller;



import com.example.domain.dto.user.*;
import com.example.domain.entity.FcmToken;
import com.example.jumouser.factory.UserFactory;
import com.example.jumouser.provider.LoginProvider;
import com.example.jumouser.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.domain.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.Optional;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;
    private final UserFactory userFactory;

    @ApiOperation(value = "로그인", notes = "Type : Kakao,Naver,Jumo 로그인, Dto 참조")
    @GetMapping("/login")
    public LoginResponseDto login(@ModelAttribute("loginRequestDto") LoginRequestDto requestDto){
        System.out.println(requestDto.toString());
        UserInfoDto userInfoDto = userFactory.loginSelector(requestDto.getType()).getUserInfo(requestDto);
        Optional<User> user = userFactory.loginSelector(requestDto.getType()).checkUser(userInfoDto);
        if(!user.isEmpty()){
            userService.updateFcmToken(user.get().getUserId(), user.get().getFcmToken());
        }
        LoginResponseDto responseDto = LoginResponseDto.builder()
                .user_id(user.get().getUserId())
                .name(user.get().getName())
                .email(user.get().getEmail())
                .phone(user.get().getPhone())
                .profile_img(user.get().getProfileImg())
                .ptHas(user.get().getPtHas())
                .type(user.get().getType())
                .social_id(user.get().getSocialId())
                .fcm_token(user.get().getFcmToken())
                .build();
        System.out.println(responseDto.toString());
        return responseDto;
    }

    @ApiOperation(value = "회원가입", notes = "카카오,네이버 회원가입 -> 주모")
    @PostMapping("/sign")
    public User signUp(@RequestBody SignUpRequestDto signUpRequestDto){

        Optional<User> user = userService.signUp(signUpRequestDto);
        return user.get();
    }

    @GetMapping("/email")
    public boolean emailCheck(@RequestParam String email){
        return userService.emailCheck(email);
    }

    @ApiOperation(value = "유저프로필 조회", notes = "Request : user_id , Response : Dto 참조")
    @GetMapping("/info")
    public UserProfileResponseDto getUserInfo(@RequestParam Long user_id){
        return userService.getUserProfile(user_id);
    }

    @ApiOperation(value = "프로필이미지 변경", notes = "file, user_id @Part로 보내주셈")
    @PutMapping("/profile-img")
    public String updateProfileImg(@RequestPart("image") MultipartFile file, @RequestPart Long user_id) throws IOException {
        return userService.updateProfileImg(user_id,file);
    }


}
