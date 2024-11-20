package com.example.medimateserver.controller.api;

import com.example.medimateserver.config.jwt.JwtProvider;
import com.example.medimateserver.dto.ProvinceDto;
import com.example.medimateserver.dto.TokenDto;
import com.example.medimateserver.dto.UserDto;
import com.example.medimateserver.service.*;
import com.example.medimateserver.util.GsonUtil;
import com.example.medimateserver.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/province", produces = "application/json")
public class ProvinceController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ProvinceService provinceService;
    @GetMapping
    public ResponseEntity<?> getAllProvince(HttpServletRequest request) {
        try {
            String tokenInformation = request.getHeader("Authorization");
            tokenInformation = tokenInformation.substring(7);
            UserDto user = GsonUtil.gI().fromJson(JwtProvider.gI().getUsernameFromToken(tokenInformation), UserDto.class);
                List<ProvinceDto> provinceDtoList = provinceService.findAll();
                return ResponseUtil.success(GsonUtil.gI().toJson(provinceDtoList));
        } catch (Exception ex) {
            return ResponseUtil.failed();
        }
    }

}
