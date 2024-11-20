package com.example.medimateserver.service.impl;

import com.example.medimateserver.dto.CategoryDto;
import com.example.medimateserver.dto.CouponDto;
import com.example.medimateserver.entity.Category;
import com.example.medimateserver.entity.Coupon;
import com.example.medimateserver.repository.CouponRepository;
import com.example.medimateserver.service.CouponService;
import com.example.medimateserver.util.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    CouponRepository couponRepository;

    @Override
    public List<CouponDto> findAll() {
        List<Coupon> couponList = couponRepository.findAll();
        return couponList.stream()
                .map(coupon -> ConvertUtil.gI().toDto(coupon, CouponDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CouponDto findById(Integer id) {
        return couponRepository.findById(id)
                .map(coupon -> ConvertUtil.gI().toDto(coupon, CouponDto.class))
                .orElse(null);
    }

    @Override
    public CouponDto save(CouponDto couponDto) {
        Coupon coupon = ConvertUtil.gI().toEntity(couponDto, Coupon.class);
        coupon = couponRepository.save(coupon);
        return ConvertUtil.gI().toDto(coupon, CouponDto.class);
    }

    @Override
    public CouponDto update(Integer id, CouponDto coupon) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public CouponDto findByCode(String code) {
        return null;
    }

}
