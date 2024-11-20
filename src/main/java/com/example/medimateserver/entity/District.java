package com.example.medimateserver.entity;

import com.example.medimateserver.dto.ProvinceDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "district")
@Data
@NoArgsConstructor // Thêm constructor không tham số
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_province")
    private Integer idProvince;
    @Column(name="name")
    private String name;
}
