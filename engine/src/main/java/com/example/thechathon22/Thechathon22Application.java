package com.example.thechathon22;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication
public class Thechathon22Application {

    public static void main(String[] args) {
        SpringApplication.run(Thechathon22Application.class, args);
    }

    @Bean
    @Primary
    public void firebaseInitialization() throws IOException {
        Resource resource = new ClassPathResource("binary-babas-gservice.json");
        FileInputStream serviceAccount = new FileInputStream(resource.getFile());
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }

}
