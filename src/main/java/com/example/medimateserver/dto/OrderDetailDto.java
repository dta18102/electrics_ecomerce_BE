package com.example.medimateserver.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDetailDto {
    private Integer idOrder;
    private Integer idProduct;
    private Integer discountPrice;
    private Integer productPrice;
    private Integer quantity;
    private ProductDto product;
    private OrderDto orders;
    private Boolean isFeedback;
}
