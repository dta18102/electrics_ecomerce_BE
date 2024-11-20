package com.example.medimateserver.service;

import com.example.medimateserver.dto.DistrictDto;
import com.example.medimateserver.dto.WardDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WardService {
    List<WardDto> findAll();
    WardDto findById(Integer id);
    List<WardDto> findByIdDistrict(Integer id);

}
