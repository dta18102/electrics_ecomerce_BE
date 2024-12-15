package com.example.medimateserver.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class OrderDto {
    private Integer id;
    private Integer idUser;
    private UserDto user;
    private Integer idCoupon;
    private String code;
    private String paymentMethod;
    private Integer discountCoupon;
    private Integer discountProduct;
    private Date orderTime;
    private String note;
    private Integer point;
    private Integer total;
    private String userAddress;
    private Integer status;
}
