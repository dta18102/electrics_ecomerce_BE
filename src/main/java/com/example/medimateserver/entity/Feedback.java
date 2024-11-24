package com.example.medimateserver.entity;

import com.example.medimateserver.dto.ProductDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "feedback")
@Data
@NoArgsConstructor // Thêm constructor không tham số
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="id_user")
    private Integer idUser;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_user", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @Column(name="id_product")
    private Integer idProduct;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_product", referencedColumnName = "id", insertable = false, updatable = false)
    private Product product;

    @Column(name="star")
    private Integer star;
    @Column(name="description")
    private String description;
    @Column(name="reply")
    private String reply;
}
