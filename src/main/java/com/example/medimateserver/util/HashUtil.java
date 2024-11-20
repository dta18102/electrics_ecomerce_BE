package com.example.medimateserver.util;

import com.example.medimateserver.dto.UserDto;
import com.google.gson.Gson;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashUtil {
    private static BCryptPasswordEncoder gson;

    public static BCryptPasswordEncoder gI() {
        // Trả về thể hiện duy nhất của lớp
        if (gson == null) {
            gson = new BCryptPasswordEncoder();
        }
        return gson;
    }

}
