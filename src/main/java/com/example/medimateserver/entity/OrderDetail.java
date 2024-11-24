package com.example.medimateserver.entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "order_detail")
@Data
@NoArgsConstructor
public class OrderDetail {

    @EmbeddedId
    private OrderDetailId id;

    @Column(name = "discount_price")
    private Integer discountPrice;

    @Column(name = "product_price")
    private Integer productPrice;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "is_feedback")
    private Boolean isFeedback;

    @ManyToOne
    @MapsId("idOrder")
    @JoinColumn(name = "id_order", referencedColumnName = "id", insertable = false, updatable = false)
    private Orders orders;

    @ManyToOne
    @MapsId("idProduct")
    @JoinColumn(name = "id_product", referencedColumnName = "id", insertable = false, updatable = false)
    private Product product;


    public OrderDetail(OrderDetailId id, Integer productPrice, Integer discountPrice, Integer quantity, Boolean isFeedback) {
        this.id = id;
        this.productPrice = productPrice;
        this.discountPrice = discountPrice;
        this.quantity = quantity;
        this.isFeedback = isFeedback;
    }


    // Lớp khóa chính kép (không thay đổi)
    @Embeddable
    @Data
    @NoArgsConstructor
    public static class OrderDetailId implements Serializable {
        @Column(name = "id_order")
        private Integer idOrder;
        @Column(name = "id_product")
        private Integer idProduct;

        public OrderDetailId(Integer idOrder, Integer idProduct) {
            this.idOrder = idOrder;
            this.idProduct = idProduct;
        }

        public Integer getIdOrder() {
            return idOrder;
        }

        public void setIdOrder(Integer idOrder) {
            this.idOrder = idOrder;
        }

        public Integer getIdProduct() {
            return idProduct;
        }

        public void setIdProduct(Integer idProduct) {
            this.idProduct = idProduct;
        }
    }

}

