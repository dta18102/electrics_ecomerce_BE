package com.example.medimateserver.service;

import com.example.medimateserver.dto.CategoryDto;
import com.example.medimateserver.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CategoryService {

    List<CategoryDto> findAll();

    CategoryDto findById(Integer id);

    CategoryDto save(CategoryDto categoryDto);

    CategoryDto update(Integer id, CategoryDto categoryDto);

    void deleteById(Integer id);
}
