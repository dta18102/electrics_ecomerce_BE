package com.example.medimateserver.config.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.Arrays;

@Data
@NoArgsConstructor
public class Generate {
    private static final String SCOPES = "https://www.googleapis.com/auth/firebase.messaging";
    private String accessToken;


    public void gen() {
        System.out.println("Firebase App initialized successfully!");
    }

    public String getAccessToken() {
        try {
            ClassPathResource resource = new ClassPathResource("serviceAccountKey.json");
            InputStream serviceAccountStream = resource.getInputStream();

            GoogleCredentials googleCredentials = GoogleCredentials
                    .fromStream(serviceAccountStream)
                    .createScoped(Arrays.asList(SCOPES));
            googleCredentials.refresh();

            return googleCredentials.getAccessToken().getTokenValue();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return exception.getMessage();
        }

    }

    public void initializeFirebaseApp() {
        try {

            ClassPathResource resource = new ClassPathResource("serviceAccountKey.json");
            InputStream serviceAccountStream = resource.getInputStream();

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}