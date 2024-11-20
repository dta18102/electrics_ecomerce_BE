package com.example.medimateserver.controller.api;

import com.example.medimateserver.config.jwt.JwtProvider;
import com.example.medimateserver.dto.TokenDto;
import com.example.medimateserver.dto.UserDto;
import com.example.medimateserver.entity.User;
import com.example.medimateserver.service.TokenService;
import com.example.medimateserver.service.UserService;
import com.example.medimateserver.util.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/user", produces = "application/json")
//@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @GetMapping
    public ResponseEntity<?> getInfoUser(HttpServletRequest request) {
        try {
            // Kiểm tra xem token gửi lên
            String tokenInformation = request.getHeader("Authorization").substring(7);
            UserDto user = GsonUtil.gI().fromJson(JwtProvider.gI().getUsernameFromToken(tokenInformation), UserDto.class);
            UserDto userFromToken = GsonUtil.gI().fromJson(JwtProvider.gI().getUsernameFromToken(tokenInformation), UserDto.class);
            UserDto userFromDatabase = userService.findById(userFromToken.getId());
            userFromDatabase.setPassword("********");
            if (user == null) {
                return ResponseUtil.failed(403);
            }
            return ResponseUtil.success(GsonUtil.gI().toJson(userFromDatabase));
        } catch (Exception ex) {
            return ResponseUtil.failed();
        }
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto) {
        userDto.setIdRole(2);
        userDto.setStatus(1);
        userDto.setRank("Đồng");
        userDto.setPoint(0);
        try {
            UserDto createdUser = userService.save(userDto);
            return ResponseUtil.success(GsonUtil.gI().toJson(createdUser));
        } catch (Exception ex) {
            return ResponseUtil.failed();
        }
    }

    @PutMapping
    public ResponseEntity<?> updateUser(HttpServletRequest request, @RequestBody UserDto userDto) {
        try {
            String tokenInformation = request.getHeader("Authorization").substring(7);
            UserDto user = GsonUtil.gI().fromJson(JwtProvider.gI().getUsernameFromToken(tokenInformation), UserDto.class);
            UserDto updatedUser = userService.update(user.getId(), userDto);
            if (updatedUser == null) {
                return ResponseUtil.failed(403);
            }
            return ResponseUtil.success();
        } catch (Exception ex) {
            return ResponseUtil.failed();
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        try {
            userService.deleteById(id);
            return ResponseUtil.success();
        } catch (Exception ex) {
            return ResponseUtil.failed(403);
        }

    }
}
