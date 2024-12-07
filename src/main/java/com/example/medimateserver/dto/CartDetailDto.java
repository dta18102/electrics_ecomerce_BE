package com.example.medimateserver.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
public class CartDetailDto {
    private Integer quantity;
    private UserDto user;
    private ProductDto product;
    private Timestamp create_at;
}
