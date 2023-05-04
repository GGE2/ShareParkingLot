package com.example.jumouser.service.impl;

import com.example.domain.dto.user.SignUpRequestDto;
import com.example.domain.dto.user.UserProfileResponseDto;
import com.example.domain.entity.FcmToken;
import com.example.domain.entity.User;
import com.example.domain.repo.NotiRepo;
import com.example.domain.repo.UserRepo;
import com.example.jumouser.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final NotiRepo notiRepo;
    @Value("${spring.cloud.gcp.storage.bucket}")
    private String drawingStorage;

    private final Storage storage;
    public boolean emailCheck(String email){
        Optional<User> user = userRepo.findByEmail(email);
        if(!user.isPresent()){
            return true;
        }
        return false;
    }


    @Transactional
    public Optional<User> signUp(SignUpRequestDto requestDto){
        User user = new User(requestDto);
        userRepo.save(user);
        Optional<User> sign_user = userRepo.findByEmail(requestDto.getEmail()); // 수정할 것
        if(sign_user.isPresent()){
            return sign_user;
        }else{
            return Optional.of(new User());
        }
    }

    public UserProfileResponseDto getUserProfile(Long user_id){

        Map<String,Object> result =  userRepo.selectProfileByUserId(user_id);

        ObjectMapper mapper = new ObjectMapper();
        UserProfileResponseDto responseDto = mapper.convertValue(result,UserProfileResponseDto.class);

        return responseDto;
    }

    @Transactional
    @Override
    public String updateProfileImg(Long user_id, MultipartFile file) throws IOException {
        Optional<User> user = userRepo.findById(user_id);
        if(!user.isEmpty()){
            if(file.isEmpty()) {
                user.get().updateProfileImg("");
                userRepo.save(user.get());
                return "";
            }else {
                String uuid = UUID.randomUUID().toString();
                String type = file.getContentType();

                BlobInfo blobInfo = storage.create(
                        BlobInfo.newBuilder(drawingStorage, uuid)
                                .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER))))
                                .setContentType(type)
                                .build(),
                        file.getInputStream()
                );
                String url = "https://storage.googleapis.com/bucket_lunamires/" + uuid;

                user.get().updateProfileImg(url);
                userRepo.save(user.get());
                return url;
            }
        }

        else{


        }
        return null;
    }

    @Override
    public Boolean updateFcmToken(Long user_id, String fcm_token) {
        notiRepo.save(new FcmToken(user_id,fcm_token));
        System.out.println(notiRepo.findById(user_id).get().getToken());
        return true;
    }
}
