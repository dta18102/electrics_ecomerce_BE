package com.example.medimateserver.service.impl;

import com.example.medimateserver.dto.ProvinceDto;
import com.example.medimateserver.entity.Coupon;
import com.example.medimateserver.entity.Province;
import com.example.medimateserver.repository.CouponDetailRepository;
import com.example.medimateserver.repository.ProvinceRepository;
import com.example.medimateserver.service.ProvinceService;
import com.example.medimateserver.util.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ProvinceServiceImpl implements ProvinceService {
    @Autowired
    private ProvinceRepository provinceRepository;

    @Override
    public List<ProvinceDto> findAll() {
        List<Province> provinceList = provinceRepository.findAll();
        return provinceList.stream()
                .map(province -> ConvertUtil.gI().toDto(province, ProvinceDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProvinceDto findById(Integer id) {
        return provinceRepository.findById(id)
                .map(coupon -> ConvertUtil.gI().toDto(coupon, ProvinceDto.class))
                .orElse(null);
    }
}
