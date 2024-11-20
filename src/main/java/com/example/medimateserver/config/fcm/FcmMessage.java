package com.example.medimateserver.config.fcm;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FcmMessage {
    private Notification notification;
    private String token;
}
