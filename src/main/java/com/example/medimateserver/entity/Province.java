package com.example.medimateserver.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "province")
@Data
@NoArgsConstructor // Thêm constructor không tham số
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="name")
    private String name;
}
