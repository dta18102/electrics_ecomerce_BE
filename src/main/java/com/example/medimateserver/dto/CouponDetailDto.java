package com.example.medimateserver.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
public class CouponDetailDto {
    private Integer id;
    private Integer idUser;
    private Integer idOrder;
    private Date startTime;
    private Date endTime;
    private Integer status;
    private CouponDto coupon;
}
