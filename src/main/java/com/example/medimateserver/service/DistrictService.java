package com.example.medimateserver.service;

import com.example.medimateserver.dto.CouponDetailDto;
import com.example.medimateserver.dto.DistrictDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DistrictService {
    List<DistrictDto> findAll();
    DistrictDto findById(Integer id);
    List<DistrictDto> findByIdProvince(Integer id);
}
