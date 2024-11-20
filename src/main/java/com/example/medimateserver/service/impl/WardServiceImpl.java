package com.example.medimateserver.service.impl;

import com.example.medimateserver.dto.CouponDto;
import com.example.medimateserver.dto.WardDto;
import com.example.medimateserver.dto.WardDto;
import com.example.medimateserver.entity.Ward;
import com.example.medimateserver.entity.Ward;
import com.example.medimateserver.repository.WardRepository;
import com.example.medimateserver.service.WardService;
import com.example.medimateserver.util.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class WardServiceImpl implements WardService {
    @Autowired
    private WardRepository wardRepository;

    @Override
    public List<WardDto> findAll() {
        List<Ward> WardList = wardRepository.findAll();
        return WardList.stream()
                .map(Ward -> ConvertUtil.gI().toDto(Ward, WardDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public WardDto findById(Integer id) {
        return wardRepository.findById(id)
                .map(coupon -> ConvertUtil.gI().toDto(coupon, WardDto.class))
                .orElse(null);
    }
    @Override
    public List<WardDto> findByIdDistrict(Integer id) {
        List<Ward> WardList = wardRepository.findByIdDistrict(id);
        return WardList.stream()
                .map(ward -> ConvertUtil.gI().toDto(ward, WardDto.class))
                .collect(Collectors.toList());
    }
}
