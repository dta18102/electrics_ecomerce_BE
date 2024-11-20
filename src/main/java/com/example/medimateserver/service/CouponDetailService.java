package com.example.medimateserver.service;

import com.example.medimateserver.dto.CouponDetailDto;
import com.example.medimateserver.entity.CouponDetail;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CouponDetailService {

    List<CouponDetail> findAll();
    CouponDetail findById(Integer id);
    List<CouponDetailDto> findByUserId(Integer id);
    List<CouponDetailDto> findByUserIdSatus0(Integer id);
    CouponDetailDto save(CouponDetailDto couponDetail);

    CouponDetailDto update(Integer id, CouponDetailDto couponDetail);

    void deleteById(Integer id);
}
