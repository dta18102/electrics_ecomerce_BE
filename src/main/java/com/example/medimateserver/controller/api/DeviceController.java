package com.example.medimateserver.controller.api;

import com.example.medimateserver.config.jwt.JwtProvider;
import com.example.medimateserver.dto.DeviceDto;
import com.example.medimateserver.dto.UserDto;
import com.example.medimateserver.service.DeviceService;
import com.example.medimateserver.util.GsonUtil;
import com.example.medimateserver.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/device", produces = "application/json")
public class DeviceController {
    @Autowired
    DeviceService deviceService;
    @PostMapping
    public ResponseEntity<?> getAllDevice(HttpServletRequest request, @RequestBody DeviceDto deviceDto) {
        try {
            System.out.println(deviceDto.getToken());
            String tokenInformation = request.getHeader("Authorization");
            tokenInformation = tokenInformation.substring(7);
            UserDto user = GsonUtil.gI().fromJson(JwtProvider.gI().getUsernameFromToken(tokenInformation), UserDto.class);
            deviceDto.setIdUser(user.getId());
            String json = GsonUtil.gI().toJson(deviceService.save(deviceDto));
            return ResponseUtil.success(json);
        } catch (Exception ex) {
            return ResponseUtil.failed();
        }
    }
}
