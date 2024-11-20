package com.example.medimateserver.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coupon")
@Data
@NoArgsConstructor // Thêm constructor không tham số
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="code")
    private String code;
    @Column(name="description")
    private String description;
    @Column(name="point")
    private Integer point;
    @Column(name="discount_percent")
    private Integer discountPercent;
    @Column(name="expiration_time")
    private Integer expirationTime;
    @Column(name="image")
    private String image;
    @Column(name="status")
    private Integer status;
}
