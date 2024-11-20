package com.example.medimateserver.service;

import com.example.medimateserver.dto.UnitDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UnitService {
    List<UnitDto> findAll();
    List<UnitDto> findWithPageAndSize(int page, int size);
    UnitDto findById(Integer id);

    UnitDto save(UnitDto UnitDto);

    UnitDto update(Integer id, UnitDto UnitDto);

    void deleteById(Integer id);
}
