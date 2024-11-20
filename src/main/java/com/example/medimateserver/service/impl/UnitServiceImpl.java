package com.example.medimateserver.service.impl;

import com.example.medimateserver.dto.UnitDto;
import com.example.medimateserver.entity.Unit;
import com.example.medimateserver.repository.UnitRepository;
import com.example.medimateserver.service.UnitService;
import com.example.medimateserver.util.ConvertUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UnitServiceImpl implements UnitService {
    @Autowired
    private UnitRepository unitRepository;

    @Override
    public List<UnitDto> findAll() {
        List<Unit> UnitList = unitRepository.findAll();
        return UnitList
                .stream()
                .map(Unit -> ConvertUtil.gI().toDto(Unit, UnitDto.class))
                .collect(Collectors.toList());
    }

    public List<UnitDto> findWithPageAndSize(int page, int size) {
        // Tạo Pageable object
        Pageable pageable = PageRequest.of(page, size);
        // Truy vấn dữ liệu
        List<Unit> Units = (List<Unit>) unitRepository.findAll(pageable);
        return Units
                .stream()
                .map(Unit -> ConvertUtil.gI().toDto(Unit, UnitDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UnitDto findById(Integer id) {

        return unitRepository.findById(id)
                .map(Unit -> ConvertUtil.gI().toDto(Unit, UnitDto.class))
                .orElse(null);
    }




    @Override
    public UnitDto save(UnitDto UnitDto) {
        Unit Unit = ConvertUtil.gI().toEntity(UnitDto, Unit.class);
        Unit = unitRepository.save(Unit);
        return ConvertUtil.gI().toDto(Unit, UnitDto.class);
    }

    @Override
    public UnitDto update(Integer id, UnitDto UnitDto) {
        Unit existingUnit = unitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
        Unit updatedCategory = unitRepository.save(existingUnit);
        return ConvertUtil.gI().toDto(updatedCategory, UnitDto.class);
    }

    @Override
    public void deleteById(Integer id) {

    }

}
