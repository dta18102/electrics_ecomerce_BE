package com.example.medimateserver.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    @Column(name = "id_user")
    private Integer idUser;
    @Column(name = "id_order")
    private Integer idOrder;
    @Column(name = "start_time")
    private Date startTime;
    @Column(name = "end_time")
    private Date endTime;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "name")
    private String name;
}
