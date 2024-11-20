package com.example.medimateserver.controller.api;

import com.example.medimateserver.config.jwt.JwtProvider;
import com.example.medimateserver.dto.AddressDto;
import com.example.medimateserver.dto.NotificationDto;
import com.example.medimateserver.dto.UserDto;
import com.example.medimateserver.entity.Notification;
import com.example.medimateserver.service.NotificationService;
import com.example.medimateserver.util.GsonUtil;
import com.example.medimateserver.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/notification", produces = "application/json")
public class NotificationController {
    @Autowired
    NotificationService notificationService;
    @GetMapping
    public ResponseEntity<?> getAllNotification(HttpServletRequest request) {
        try {
            String tokenInformation = request.getHeader("Authorization").substring(7);
            UserDto user = GsonUtil.gI().fromJson(JwtProvider.gI().getUsernameFromToken(tokenInformation), UserDto.class);
            List<NotificationDto> notificationDtos = notificationService.findByIdUser(user.getId());
            String jsons = GsonUtil.gI().toJson(notificationDtos);
            return ResponseUtil.success(jsons);
        } catch (Exception ex) {
            return ResponseUtil.failed();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateNotification(HttpServletRequest request, @PathVariable Integer id) {
        try {
            String tokenInformation = request.getHeader("Authorization").substring(7);
            UserDto user = GsonUtil.gI().fromJson(JwtProvider.gI().getUsernameFromToken(tokenInformation), UserDto.class);
            NotificationDto notificationDto = notificationService.findById(id);
            NotificationDto notificationDtos = notificationService.save(notificationDto);
            String jsons = GsonUtil.gI().toJson(notificationDtos);
            return ResponseUtil.success(jsons);
        } catch (Exception ex) {
            return ResponseUtil.failed();
        }
    }
    @PostMapping
    public ResponseEntity<?> addNotification(HttpServletRequest request, @RequestBody NotificationDto notificationDto) {
        try {
            String tokenInformation = request.getHeader("Authorization").substring(7);
            UserDto user = GsonUtil.gI().fromJson(JwtProvider.gI().getUsernameFromToken(tokenInformation), UserDto.class);
            notificationDto.setIdUser(user.getId());
            NotificationDto notificationDtos = notificationService.add(notificationDto);
            String jsons = GsonUtil.gI().toJson(notificationDtos);
            return ResponseUtil.success(jsons);
        } catch (Exception ex) {
            return ResponseUtil.failed();
        }
    }
    @PostMapping("/all")
    public ResponseEntity<?> updateAllNotification(HttpServletRequest request) {
        try {
            String tokenInformation = request.getHeader("Authorization").substring(7);
            UserDto user = GsonUtil.gI().fromJson(JwtProvider.gI().getUsernameFromToken(tokenInformation), UserDto.class);
            boolean check = notificationService.changeAllStatus(user.getId());
            if (check) {
                return ResponseUtil.success();
            } else {
                return ResponseUtil.failed();
            }
        } catch (Exception ex) {
            return ResponseUtil.failed();
        }
    }
}
