package com.example.medimateserver.entity;

import com.example.medimateserver.dto.CartDetailDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "cart_detail")
@Data
@NoArgsConstructor // Thêm constructor không tham số
public class CartDetail {
    @EmbeddedId
    private CartDetail.CartDetailId id;


    @Column(name = "quantity")
    private Integer quantity;

    public CartDetail(CartDetailId id, Integer quantity) {
        this.id  = id;
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public CartDetailId getId() {
        return id;
    }

    public void setId(CartDetailId id) {
        this.id = id;
    }

    @ManyToOne
    @MapsId("idUser")
    @JoinColumn(name = "id_user", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @MapsId("idProduct")
    @JoinColumn(name = "id_product", referencedColumnName = "id", insertable = false, updatable = false)
    private Product product;

    public CartDetail(CartDetailId id, Integer discountPrice, Integer quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    public static class CartDetailId implements Serializable {
        @Column(name = "id_user")
        private Integer idUser;
        @Column(name = "id_product")
        private Integer idProduct;

        public CartDetailId(Integer idUser, Integer idProduct) {
            this.idUser = idUser;
            this.idProduct = idProduct;
        }

        public Integer getIdUser() {
            return idUser;
        }

        public void setIdUser(Integer idUser) {
            this.idUser = idUser;
        }

        public Integer getIdProduct() {
            return idProduct;
        }

        public void setIdProduct(Integer idProduct) {
            this.idProduct = idProduct;
        }
    }
}
