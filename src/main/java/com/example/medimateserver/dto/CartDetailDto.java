package com.example.medimateserver.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartDetailDto {
    private Integer quantity;
    private UserDto user;
    private ProductDto product;
}
