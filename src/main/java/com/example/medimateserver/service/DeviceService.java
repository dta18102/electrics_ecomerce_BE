package com.example.medimateserver.service;

import com.example.medimateserver.dto.DeviceDto;
import com.example.medimateserver.dto.OrderDetailDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface DeviceService {
    DeviceDto save(DeviceDto deviceDto);
    DeviceDto findById(Integer id);
}
