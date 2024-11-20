package com.example.medimateserver.controller.web;


import com.example.medimateserver.config.fcm.Generate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class TestSms {
    @GetMapping(value = "/sendSMS")
    public String generateOTP() throws IOException {
        Generate generate = new Generate();
        System.out.println(generate.getAccessToken());
        return generate.getAccessToken();
    }



}
