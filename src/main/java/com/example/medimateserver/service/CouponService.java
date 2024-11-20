package com.example.medimateserver.service;

import com.example.medimateserver.dto.CouponDto;
import com.example.medimateserver.entity.Coupon;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CouponService {

    List<CouponDto> findAll();

    CouponDto findById(Integer id);

    CouponDto save(CouponDto couponDto);

    CouponDto update(Integer id, CouponDto couponDto);

    void deleteById(Integer id);

    CouponDto findByCode(String code);
}
