package com.example.notification.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import java.io.FileInputStream;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Value("${Firebase-Key}")
    private ClassPathResource resource;
    private FirebaseApp firebaseApp;
    @PostConstruct
    public void initFirebase() {
        try {

            // Service Account를 이용하여 Fireabse Admin SDK 초기화
            InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("jumo-google.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            firebaseApp= FirebaseApp.initializeApp(options);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Bean
    FirebaseMessaging firebaseMessaging(){
        return FirebaseMessaging.getInstance(firebaseApp);
    }
}
