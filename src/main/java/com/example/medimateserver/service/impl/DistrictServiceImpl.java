package com.example.medimateserver.service.impl;

import com.example.medimateserver.dto.DistrictDto;
import com.example.medimateserver.dto.CouponDto;
import com.example.medimateserver.dto.DistrictDto;
import com.example.medimateserver.entity.District;
import com.example.medimateserver.entity.District;
import com.example.medimateserver.repository.DistrictRepository;
import com.example.medimateserver.service.DistrictService;
import com.example.medimateserver.util.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DistrictServiceImpl implements DistrictService {
    @Autowired
    private DistrictRepository districtRepository;

    @Override
    public List<DistrictDto> findAll() {
        List<District> DistrictList = districtRepository.findAll();
        return DistrictList.stream()
                .map(District -> ConvertUtil.gI().toDto(District, DistrictDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public DistrictDto findById(Integer id) {
        return districtRepository.findById(id)
                .map(coupon -> ConvertUtil.gI().toDto(coupon, DistrictDto.class))
                .orElse(null);
    }

    @Override
    public List<DistrictDto> findByIdProvince(Integer id) {
        List<District> DistrictList = districtRepository.findByIdProvince(id);
        return DistrictList.stream()
                .map(District -> ConvertUtil.gI().toDto(District, DistrictDto.class))
                .collect(Collectors.toList());
    }

}
