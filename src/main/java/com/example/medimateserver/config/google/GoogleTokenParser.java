package com.example.medimateserver.config.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.util.Collections;

public class GoogleTokenParser {
    private static final String CLIENT_ID = "929394835463-1dqh7n748mbfk7dbrrbms1b344r079av.apps.googleusercontent.com";

    public static Payload parseToken(String token) {
        try {
            JsonFactory jsonFactory = new JacksonFactory();
            HttpTransport transport = new NetHttpTransport();
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    .build();
            GoogleIdToken idToken = verifier.verify(token);
            if (idToken != null) {
                return idToken.getPayload();
            } else {
                System.out.println("Invalid ID token.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}