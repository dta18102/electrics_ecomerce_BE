package com.example.medimateserver.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_category")
    private Integer idCategory;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "discount_percent")
    private Integer discountPercent;

    @Column(name = "price")
    private Integer price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "image")
    private String image;

    @Column(name = "status")
    private Integer status;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_unit", referencedColumnName = "id", insertable = false, updatable = false)
    private Unit unit;

    // Quan hệ n-1 với Category
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_category", referencedColumnName = "id", insertable = false, updatable = false)
    private Category category;

}
