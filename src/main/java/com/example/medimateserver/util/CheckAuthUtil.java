package com.example.medimateserver.util;

import com.example.medimateserver.config.jwt.JwtProvider;
import com.example.medimateserver.dto.TokenDto;
import com.example.medimateserver.dto.UserDto;
import com.example.medimateserver.service.TokenService;
import com.example.medimateserver.service.impl.TokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckAuthUtil {
    private static CheckAuthUtil checkAuthUtil;

    public static CheckAuthUtil gI() {
        if (checkAuthUtil == null) {
            checkAuthUtil = new CheckAuthUtil();
        }
        return checkAuthUtil;
    }
    public boolean check(String token, String databaseToken, Integer id) {
        String oldToken = token;
        token = JwtProvider.gI().getUsernameFromToken(token);
        UserDto user = GsonUtil.gI().fromJson(token, UserDto.class);
        if (user.getId() == id && oldToken.equals(databaseToken)) {
            return true;
        }
        return false;
    }

}
