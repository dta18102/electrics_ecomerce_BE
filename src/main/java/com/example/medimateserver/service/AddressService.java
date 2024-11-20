package com.example.medimateserver.service;

import com.example.medimateserver.dto.AddressDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {
    List<AddressDto> findAll();

    AddressDto findById(Integer id);

    AddressDto save(Integer id, AddressDto addressDto);

    AddressDto update(Integer id, AddressDto addressDto);

    void deleteById(Integer id);

    List<AddressDto> findByIdUser(Integer id);
}
