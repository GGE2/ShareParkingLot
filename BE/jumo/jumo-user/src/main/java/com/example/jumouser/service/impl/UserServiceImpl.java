package com.example.jumouser.service.impl;

import com.example.jumouser.service.UserService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    public String createFirebaseCustomToken(Map<String,Object> userInfo) throws Exception {

        UserRecord userRecord;
        String uid = userInfo.get("id").toString();
        String email = userInfo.get("email").toString();
        String displayName = userInfo.get("nickname").toString();

        // 1. 사용자 정보로 파이어 베이스 유저정보 update, 사용자 정보가 있다면 userRecord에 유저 정보가 담긴다.
//        try {
//            UpdateRequest request = new UpdateRequest(uid);
//            request.setEmail(email);
//            request.setDisplayName(displayName);
//            userRecord = FirebaseAuth.getInstance().updateUser(request);

            // 1-2. 사용자 정보가 없다면 > catch 구분에서 createUser로 사용자를 생성한다.
//        } catch (FirebaseAuthException e) {

//            CreateRequest createRequest = new CreateRequest();
//            createRequest.setUid(uid);
//            createRequest.setEmail(email);
//            createRequest.setEmailVerified(false);
//            createRequest.setDisplayName(displayName);

//            userRecord = FirebaseAuth.getInstance().createUser(createRequest);
//        }

        // 2. 전달받은 user 정보로 CustomToken을 발행한다.
//        return FirebaseAuth.getInstance().createCustomToken(userRecord.getUid());
        return null;
    }
}
