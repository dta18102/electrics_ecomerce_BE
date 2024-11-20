package com.example.medimateserver.entity;

import com.example.medimateserver.dto.DistrictDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ward")
@Data
@NoArgsConstructor // Thêm constructor không tham số
public class Ward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="id_district")
    private Integer idDistrict;
    @Column(name="name")
    private String name;
}
