package com.example.medimateserver.repository;

import com.example.medimateserver.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
//    @Query("SELECT p FROM Product p WHERE " +
//            "(:idCategory IS NULL OR p.category.id = :idCategory) AND " +
//            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
//            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
//            "(:keySearch IS NULL OR (p.name LIKE CONCAT('%', :keySearch, '%')))"
//    )
//    Page<Product> findWithFilter(
//            @Param("idCategory") Integer idCategory,
//            @Param("minPrice") Integer minPrice,
//            @Param("maxPrice")Integer maxPrice,
//            @Param("keySearch") String keySearch,
//            Pageable pageable);

//    @Query("SELECT p FROM Product p " +
//            "WHERE ((:idCategory IS NULL OR p.category.id = :idCategory) " +
//            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
//            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
//            "AND (:keySearch IS NULL OR p.name LIKE CONCAT('%', :keySearch, '%'))) " +
//            "OR ((:keySearch IS NULL OR p.category.name LIKE CONCAT('%', :keySearch, '%')) " +
//            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
//            "AND (:maxPrice IS NULL OR p.price <= :maxPrice)) " +
//            "ORDER BY p.id ASC " +
//            "LIMIT :pageSize OFFSET :offset")
//    List<Product> findWithFilterTraditional(
//            @Param("idCategory") Integer idCategory,
//            @Param("minPrice") Integer minPrice,
//            @Param("maxPrice") Integer maxPrice,
//            @Param("keySearch") String keySearch,
//            @Param("pageSize") Integer pageSize,
//            @Param("offset") Integer offset);

//    @Query("SELECT DISTINCT p FROM Product p " +
//            "WHERE (:idCategory IS NULL OR p.category.id = :idCategory) " +
//            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
//            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
//            "AND (:keySearch IS NULL OR p.name LIKE CONCAT('%', :keySearch, '%') " +
//            "OR (:keySearch IS NULL OR p.category.name LIKE CONCAT('%', :keySearch, '%')) "
////            "OR (:keySearch IS NOT NULL AND CAST(:keySearch AS int) IS NOT NULL AND p.category.id = CAST(:keySearch AS int)) )"
//    )
    @Query("SELECT DISTINCT p FROM Product p " +
            "WHERE (:idCategory IS NULL OR p.category.id = :idCategory) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
            "AND (:keySearch IS NULL OR p.name LIKE CONCAT('%', :keySearch, '%')) "
    )
    List<Product> findWithFilterTraditional(
            @Param("idCategory") Integer idCategory,
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice,
            @Param("keySearch") String keySearch);


    @Query("SELECT p FROM Product p ORDER BY p.id DESC LIMIT 10")
    List<Product> getNewProduct();

    @Query(value = "SELECT p.*, SUM(od.quantity) AS sales_count " +
            "FROM order_detail od " +
            "JOIN product p ON od.id_product = p.id " +
            "WHERE p.status = 1 " +
            "GROUP BY od.id_product, p.id, p.name " +
            "ORDER BY sales_count DESC " +
            "LIMIT 50", nativeQuery = true)
    List<Product> getBestSellersProduct();

    @Query(value = "SELECT DISTINCT p.* " + // Assuming 'product' is the entity name
            "FROM Product p " +
            "JOIN order_detail od ON od.id_product = p.id " +
            "JOIN orders o ON o.id = od.id_order " +
            "WHERE o.status = 1 " +
            "AND p.status = 1 " +
            "GROUP BY p.id",
            nativeQuery = true)
    List<Product> getHaveSoldProduct();

    @Query(value = "SELECT p.* " +
            "FROM Product p " +
            "WHERE p.status = 1 " +
            "GROUP BY p.id " +
            "ORDER BY p.discount_percent DESC " +
            "LIMIT 10",
            nativeQuery = true)
    List<Product> getBestPromotionProduct();
    @Query(value = "SELECT p.* " +
            "FROM Product p " +
            "WHERE p.status = 1 " +
            "AND p.discount_percent > 0 " +
            "GROUP BY p.id " +
            "ORDER BY p.discount_percent DESC",
            nativeQuery = true)
    List<Product> getHavePromotionProduct();
}
