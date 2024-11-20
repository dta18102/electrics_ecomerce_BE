package com.example.medimateserver.controller.api;

import com.example.medimateserver.config.jwt.JwtProvider;
import com.example.medimateserver.dto.*;
import com.example.medimateserver.entity.CouponDetail;
import com.example.medimateserver.entity.Orders;
import com.example.medimateserver.service.CouponDetailService;
import com.example.medimateserver.service.TokenService;
import com.example.medimateserver.util.GsonUtil;
import com.example.medimateserver.util.ResponseUtil;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/coupon_detail", produces = "application/json")
public class CouponDetailController {
    @Autowired
    private CouponDetailService couponDetailService;
    @Autowired
    private TokenService tokenService;
    @GetMapping
    public ResponseEntity<?> getAllCouponDetail(HttpServletRequest request) {
        try {
            String tokenInformation = request.getHeader("Authorization");
            tokenInformation = tokenInformation.substring(7);
            UserDto user = GsonUtil.gI().fromJson(JwtProvider.gI().getUsernameFromToken(tokenInformation), UserDto.class);
            String jsons = GsonUtil.gI().toJson(couponDetailService.findByUserId(user.getId()));
            return ResponseUtil.success(jsons);
        } catch (Exception ex) {
            return ResponseUtil.failed();
        }
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllCouponDetailSatus0(HttpServletRequest request) {
        try {
            String tokenInformation = request.getHeader("Authorization");
            tokenInformation = tokenInformation.substring(7);
            UserDto user = GsonUtil.gI().fromJson(JwtProvider.gI().getUsernameFromToken(tokenInformation), UserDto.class);
            String jsons = GsonUtil.gI().toJson(couponDetailService.findByUserIdSatus0(user.getId()));
            return ResponseUtil.success(jsons);
        } catch (Exception ex) {
            return ResponseUtil.failed();
        }
    }
    @PostMapping
    public ResponseEntity<?> saveCouponDetail(HttpServletRequest request, @RequestBody CouponDto couponDto) {
        try {
            String tokenInformation = request.getHeader("Authorization");
            tokenInformation = tokenInformation.substring(7);
            UserDto user = GsonUtil.gI().fromJson(JwtProvider.gI().getUsernameFromToken(tokenInformation), UserDto.class);
            CouponDetailDto couponDetailDto = new CouponDetailDto();
            couponDetailDto.setCoupon(couponDto);
            couponDetailDto.setIdUser(user.getId());
            couponDetailService.save(couponDetailDto);
            return ResponseUtil.success();
        } catch (Exception ex) {
            return ResponseUtil.failed();
        }
    }
}
