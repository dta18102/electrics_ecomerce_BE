package com.example.medimateserver.service.impl;

import com.example.medimateserver.dto.DeviceDto;
import com.example.medimateserver.dto.ProductDto;
import com.example.medimateserver.entity.Device;
import com.example.medimateserver.entity.Product;
import com.example.medimateserver.repository.DeviceRepository;
import com.example.medimateserver.service.DeviceService;
import com.example.medimateserver.util.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceImpl implements DeviceService {
    @Autowired
    DeviceRepository deviceRepository;
    @Override
    public DeviceDto save(DeviceDto deviceDto) {
        Device deviceSaved = deviceRepository.save(ConvertUtil.gI().toEntity(deviceDto, Device.class));
        return ConvertUtil.gI().toEntity(deviceSaved, DeviceDto.class);
    }
    @Override
    public DeviceDto findById(Integer id) {
        return deviceRepository.findById(id)
                .map(device -> ConvertUtil.gI().toDto(device, DeviceDto.class))
                .orElse(null);
    }
}
