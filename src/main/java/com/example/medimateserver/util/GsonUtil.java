package com.example.medimateserver.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {
    private static Gson gson;

    public static Gson gI() {
        // Trả về thể hiện duy nhất của lớp
        if (gson == null) {
            gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX") // Set desired date format
                    .create();
        }
        return gson;
    }

}
