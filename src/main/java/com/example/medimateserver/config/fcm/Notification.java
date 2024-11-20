package com.example.medimateserver.config.fcm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    private String title;
    private String body;
}
