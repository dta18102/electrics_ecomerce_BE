package com.example.medimateserver.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
public class CouponDto {
    private Integer id;
    private String code;
    private String name;
    private String description;
    private Integer point;
    private Integer discountPercent;
    private Integer expirationTime;
    private String image;
    private Integer status;
//    private Integer idUser;
//    private Integer idOrder;
    private Date startTime;
    private Date endTime;
    private Integer quantity;
}
