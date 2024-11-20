package com.example.medimateserver.dto;

import com.example.medimateserver.entity.Product;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class CategoryDto {
    private Integer id;
    private String name;
    private Integer status;
}

