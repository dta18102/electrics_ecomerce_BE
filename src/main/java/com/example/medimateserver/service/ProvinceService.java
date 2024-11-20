package com.example.medimateserver.service;

import com.example.medimateserver.dto.CouponDto;
import com.example.medimateserver.dto.ProvinceDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ProvinceService {
    List<ProvinceDto> findAll();
    ProvinceDto findById(Integer id);
}
