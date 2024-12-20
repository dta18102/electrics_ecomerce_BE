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

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_user", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;
    @Column(name = "id_coupon")
    private Integer idCoupon;
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
