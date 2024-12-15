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
        try {
            CouponDto exitsting = couponService.findByCode(couponDto.getCode().trim());
            if(exitsting != null ){
                return ResponseUtil.failed(400,"Mã giảm giá với mã code "+couponDto.getCode()+" đã tồn tại");
            }
            couponDto.setStatus(1);
            CouponDto savedCoupon = couponService.save(couponDto);
            return ResponseUtil.success(GsonUtil.gI().toJson(savedCoupon));
        }catch (Exception ex){
            System.out.println(ex.toString());
            return ResponseUtil.failed();
        }
    }

    @PutMapping
    public ResponseEntity<?> updateCoupon( @RequestBody CouponDto coupon) {
        try {
            CouponDto exitsting = couponService.findById(coupon.getId());
            if(exitsting == null){
                return ResponseUtil.failed(400, "This coupon is not exits !");
            }
            if(coupon.getCode() != null){
                exitsting.setCode(coupon.getCode());
            }
            if(coupon.getName() != null){
                exitsting.setName(coupon.getName());
            }
            if(coupon.getDiscountPercent() != null){
                exitsting.setDiscountPercent(coupon.getDiscountPercent());
            }
            if(coupon.getPoint() != null){
                exitsting.setPoint(coupon.getPoint());
            }
            if(coupon.getImage() != null){
                exitsting.setImage(coupon.getImage());
            }
            if(coupon.getStatus() != null){
                exitsting.setStatus(coupon.getStatus());
            }
            if(coupon.getStartTime() != null){
                exitsting.setStartTime(coupon.getStartTime());
            }
            if(coupon.getEndTime() != null){
                exitsting.setEndTime(coupon.getEndTime());
            }
            if(coupon.getQuantity() != null){
                exitsting.setQuantity(coupon.getQuantity());
            }


            CouponDto savedCoupon = couponService.save(exitsting);
            return ResponseUtil.success(GsonUtil.gI().toJson(savedCoupon));

        }catch (Exception ex){
            System.out.println(ex.toString());
            return ResponseUtil.failed();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCoupon(@PathVariable Integer id) {
        try {
            CouponDto exitsting = couponService.findById(id);
            if(exitsting == null ){
                return ResponseUtil.failed(400,"Mã giảm giá với ID "+id+" không tồn tại, vui lòng load lại trang");
            }
             couponService.deleteById(id);
            return ResponseUtil.success();
        }catch (Exception ex){
            System.out.println(ex.toString());
            return ResponseUtil.failed();
        }
    }
}
