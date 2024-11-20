package com.example.medimateserver.dto;

import com.example.medimateserver.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto {
    private Integer id;
    private Integer idCategory;
    private Integer idUnit;
    private String name;
    private String description;
    private Integer discountPercent;
    private Integer price;
    private Integer quantity;
    private String image;
    private Integer status;
    private UnitDto unit;
    private CategoryDto category;
}
