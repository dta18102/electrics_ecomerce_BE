package com.example.medimateserver.controller.api;

import com.example.medimateserver.config.google.GoogleTokenParser;
import com.example.medimateserver.config.jwt.JwtProvider;
import com.example.medimateserver.dto.LoginWithGoogle;
import com.example.medimateserver.dto.ResponseDto;
import com.example.medimateserver.dto.TokenDto;
import com.example.medimateserver.dto.UserDto;
import com.example.medimateserver.entity.Token;
import com.example.medimateserver.entity.User;
import com.example.medimateserver.service.TokenService;
import com.example.medimateserver.service.UserService;
import com.example.medimateserver.util.GsonUtil;
import com.example.medimateserver.util.MLogger;
import com.example.medimateserver.util.ResponseUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping(value = "/api/auth", produces = "application/json")
public class AuthController {
    @Autowired
    UserService userService;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) throws JsonProcessingException {
        try {
            UserDto user = userService.findByPhone(userDto.getPhone());
            if (user==null) {
                return ResponseUtil.failed(401,"Invalid phone number or password, please try again!");
            }
            if (userDto.getPassword().toString().compareTo(user.getPassword().toString()) == 0 && userDto.getPhone().toString().compareTo(user.getPhone().toString()) == 0) {
                String token = JwtProvider.gI().generateToken(GsonUtil.gI().toJson(user));
                String refreshToken = JwtProvider.gI().generateRefreshToken(GsonUtil.gI().toJson(user));
                TokenDto tokenDto = new TokenDto();
                tokenDto.setAccessToken(token);
                tokenDto.setRefreshToken(refreshToken);
                String jsons = GsonUtil.gI().toJson(tokenDto);
                tokenDto.setIdUser(user.getId());
                tokenService.save(tokenDto);
                return ResponseUtil.success(jsons);
            } else {
                return ResponseUtil.failed(401);
            }
        } catch (Exception ex) {
            return ResponseUtil.failed();
        }
    }

    @PostMapping("/login_with_google")
    public ResponseEntity<?> login(@RequestBody LoginWithGoogle loginWithGoogle) throws JsonProcessingException {
        try {
            GoogleIdToken.Payload payload = GoogleTokenParser.parseToken(loginWithGoogle.getToken());
            UserDto user = userService.findByEmail(payload.getEmail());
            if (user == null) {
                user = new UserDto();
                user.setEmail(payload.getEmail());
                user.setUsername(payload.get("name").toString());
                user.setIdRole(2);
                user.setStatus(1);
                user.setRank("Đồng");
                user.setPoint(0);
                userService.save(user);
                UserDto userSaved = userService.findByEmail(payload.getEmail());
                String token = JwtProvider.gI().generateToken(GsonUtil.gI().toJson(userSaved));
                String refreshToken = JwtProvider.gI().generateRefreshToken(GsonUtil.gI().toJson(userSaved));
                TokenDto tokenDto = new TokenDto();
                tokenDto.setAccessToken(token);
                tokenDto.setRefreshToken(refreshToken);
                String jsons = GsonUtil.gI().toJson(tokenDto);
                tokenDto.setIdUser(userSaved.getId());
                tokenService.save(tokenDto);
                return ResponseUtil.success(jsons);
            } else {
                UserDto userSaved = userService.findByEmail(payload.getEmail());
                String token = JwtProvider.gI().generateToken(GsonUtil.gI().toJson(userSaved));
                String refreshToken = JwtProvider.gI().generateRefreshToken(GsonUtil.gI().toJson(userSaved));
                TokenDto tokenDto = new TokenDto();
                tokenDto.setAccessToken(token);
                tokenDto.setRefreshToken(refreshToken);
                String jsons = GsonUtil.gI().toJson(tokenDto);
                tokenDto.setIdUser(userSaved.getId());
                tokenService.save(tokenDto);
                return ResponseUtil.success(jsons);
            }
        } catch (Exception ex) {
            System.out.println("Looix" + ex.getMessage());
            return ResponseUtil.failed();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userPL) {
        try {
            UserDto exitingUserWithEmail = userService.findByEmail(userPL.getEmail());
            if(exitingUserWithEmail != null){
                return ResponseUtil.failed(400, "Email "+userPL.getEmail()+" đã được đăng ký!");
            }
            UserDto exitingUserWithPhone = userService.findByPhone(userPL.getPhone());
            if(exitingUserWithPhone != null){
                return ResponseUtil.failed(400, "SĐT "+userPL.getPhone()+" đã được đăng ký!");
            }
            userPL.setIdRole(2);
            userPL.setStatus(1);
            userPL.setRank("Đồng");
            userPL.setPoint(0);
            UserDto newUser = userService.save(userPL);
            return ResponseUtil.success(GsonUtil.gI().toJson(newUser));
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return ResponseUtil.failed();
        }
    }
    @PostMapping("/register_with_otp")
    public ResponseEntity<?> registerWithOtp(@RequestBody UserDto userPL) {
        try {
            UserDto userWithEmail = userService.findByEmail(userPL.getEmail());
            UserDto userWithPhone = userService.findByEmail(userPL.getEmail());
            if(userWithPhone != null || userWithEmail != null)
                return ResponseUtil.failed(409, "User already exists");
            if (userPL.getPhone() == null) {
                return ResponseUtil.failed();
            }
            userPL.setIdRole(2);
            userPL.setStatus(1);
            userPL.setRank("Đồng");
            userPL.setPoint(0);
            userPL.setPhone(userPL.getPhone());
            userService.save(userPL);
            return ResponseUtil.success();
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return ResponseUtil.failed();
        }
    }
    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        try {
            String tokenInformation = request.getHeader("Authorization").substring(7);
            UserDto user = GsonUtil.gI().fromJson(JwtProvider.gI().getUsernameFromToken(tokenInformation), UserDto.class);
            UserDto userDto1 = userService.findByPhone(user.getPhone());
            tokenService.deleteById(userDto1.getId());
            return ResponseUtil.success();
        } catch (Exception ex) {
             return ResponseUtil.failed();
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody TokenDto tokenDto, HttpServletRequest request) {
        try {

        // Xác thực user
            String tokenInformation = tokenDto.getRefreshToken();
            UserDto user = GsonUtil.gI().fromJson(JwtProvider.gI().getUsernameFromToken(tokenInformation), UserDto.class);
            TokenDto tokenDtoDatabase = tokenService.findById(user.getId());
            if (JwtProvider.gI().verifyRefreshToken(tokenInformation, tokenDtoDatabase)) {
                // Xác thực refreshToken thành công thì xoá token cũ trong db và trả về token mới
                tokenService.deleteById(user.getId());
                String token = JwtProvider.gI().generateToken(GsonUtil.gI().toJson(user));
                String refreshToken = JwtProvider.gI().generateRefreshToken(GsonUtil.gI().toJson(user));
                TokenDto returnTokenDto = new TokenDto();
                returnTokenDto.setAccessToken(token);
                returnTokenDto.setRefreshToken(refreshToken);
                String jsons = GsonUtil.gI().toJson(returnTokenDto);
                returnTokenDto.setIdUser(user.getId());
                tokenService.save(returnTokenDto);
                return ResponseUtil.success(jsons);
            }
             return ResponseUtil.failedExpriration();
        } catch (Exception ex) {
             return ResponseUtil.failedExpriration();
        }
    }
}
