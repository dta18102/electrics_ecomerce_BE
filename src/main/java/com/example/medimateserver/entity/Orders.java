package com.example.medimateserver.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor // Thêm constructor không tham số
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "id_user")
    private Integer idUser;
    @Column(name = "code")
    private String code;
    @Column(name = "payment_method")
    private String paymentMethod;
    @Column(name = "discount_coupon")
    private Integer discountCoupon;
    @Column(name = "discount_product")
    private Integer discountProduct;
    @Column(name = "order_time")
    private Date orderTime;
    @Column(name = "note")
    private String note;
    @Column(name = "point")
    private Integer point;
    @Column(name = "total")
    private Integer total;
    @Column(name = "user_address")
    private String userAddress;
    @Column(name = "status")
    private Integer status;
}
