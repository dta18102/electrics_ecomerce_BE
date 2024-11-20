package com.example.medimateserver.controller.api;

import com.example.medimateserver.config.jwt.JwtProvider;
import com.example.medimateserver.dto.CouponDto;
import com.example.medimateserver.dto.TokenDto;
import com.example.medimateserver.dto.UserDto;
import com.example.medimateserver.entity.Coupon;
import com.example.medimateserver.service.CouponService;
import com.example.medimateserver.service.TokenService;
import com.example.medimateserver.util.GsonUtil;
import com.example.medimateserver.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/coupon", produces = "application/json")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @Autowired
    private TokenService tokenService;

    @GetMapping
    public ResponseEntity<?> getAllCoupon(HttpServletRequest request) {
        try {
            String tokenInformation = request.getHeader("Authorization");
            tokenInformation = tokenInformation.substring(7);
            UserDto user = GsonUtil.gI().fromJson(JwtProvider.gI().getUsernameFromToken(tokenInformation), UserDto.class);
            String jsons = GsonUtil.gI().toJson(couponService.findAll());
            return ResponseUtil.success(jsons);

        } catch (Exception ex) {
            return ResponseUtil.failed();
        }
    }

    @PostMapping
    public ResponseEntity<?> createCoupon(@RequestBody CouponDto couponDto) {
        CouponDto savedCoupon = couponService.save(couponDto);
        return ResponseUtil.success();
    }

    @PutMapping
    public ResponseEntity<?> updateCoupon(@PathVariable String code, @RequestBody Coupon Coupon) {
        return ResponseUtil.success();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCoupon(@PathVariable String code) {
        return ResponseUtil.success();
    }
}
