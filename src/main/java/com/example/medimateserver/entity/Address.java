package com.example.medimateserver.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "address")
@Data
@NoArgsConstructor // Thêm constructor không tham số
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="id_user")
    private Integer idUser;
    @Column(name="full_name")
    private String fullName;
    @Column(name="phone")
    private String phone;
    @Column(name="ward")
    private String ward;
    @Column(name="district")
    private String district;
    @Column(name="province")
    private String province;
    @Column(name="type")
    private String type;
    @Column(name="is_default")
    private Boolean isDefault;
    @Column(name="specific_address")
    private String specificAddress;
    @Column(name="status")
    private Integer status;
}
