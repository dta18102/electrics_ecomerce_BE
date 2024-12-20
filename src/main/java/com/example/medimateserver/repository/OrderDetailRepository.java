package com.example.medimateserver.repository;

import com.example.medimateserver.entity.OrderDetail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetail.OrderDetailId> {
    @Query("SELECT od FROM OrderDetail od WHERE od.id.idOrder = :idOrder")
    List<OrderDetail> findByIdOrder(@Param("idOrder") Integer idOrder);
    @Modifying
    @Query(value = "INSERT INTO order_detail (id_order, id_product, discount_price, quantity) VALUES (:idOrder, :idProduct, :discountPrice, :quantity)", nativeQuery = true)
    void saveCustome(@Param("idOrder") Integer idOrder,
                          @Param("idProduct") Integer idProduct,
                          @Param("discountPrice") Integer discountPrice,
                          @Param("quantity") Integer quantity);


    @Modifying
    @Transactional
    @Query("UPDATE OrderDetail od SET od.isFeedback = true WHERE od.id.idOrder = :idOrder AND od.product.id = :idProduct")
    void updateIsFeedback(@Param("idOrder") Integer idOrder, @Param("idProduct") Integer idProduct);
//    @Modifying
//    @Query("UPDATE OrderDetail od SET od.isFeedback = true WHERE od.idUser = :idUser AND od.idProduct = :idProduct")
//    OrderDetail update(@Param("idUser") Integer idUser, @Param("idProduct") Integer idProduct);


}
