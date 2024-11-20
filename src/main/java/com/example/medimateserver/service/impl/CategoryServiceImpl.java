package com.example.medimateserver.service.impl;

import com.example.medimateserver.dto.CategoryDto;
import com.example.medimateserver.entity.Category;
import com.example.medimateserver.repository.CategoryRepository;
import com.example.medimateserver.service.CategoryService;
import com.example.medimateserver.util.ConvertUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> ConvertUtil.gI().toDto(category, CategoryDto.class))
                .collect(Collectors.toList());
    }


    @Override
    public CategoryDto findById(Integer id) {
        return categoryRepository.findById(id)
                .map(category -> ConvertUtil.gI().toDto(category, CategoryDto.class))
                .orElse(null); // Trả về null nếu không tìm thấy Category
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        Category category = ConvertUtil.gI().toEntity(categoryDto, Category.class);
        category = categoryRepository.save(category);
        return ConvertUtil.gI().toDto(category, CategoryDto.class);
    }


    @Override
    public CategoryDto update(Integer id, CategoryDto categoryDto) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));

        // Cập nhật các thuộc tính từ categoryDto sang existingCategory
        existingCategory.setName(categoryDto.getName()); // Ví dụ, gán name mới
        // Cập nhật các thuộc tính khác nếu cần

        Category updatedCategory = categoryRepository.save(existingCategory);
        return ConvertUtil.gI().toDto(updatedCategory, CategoryDto.class);
    }

    @Override
    public void deleteById(Integer id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

}
