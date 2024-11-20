package com.example.medimateserver.repository;

import com.example.medimateserver.entity.CouponDetail;
import com.example.medimateserver.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {
    
    List<District> findByIdProvince(@Param("idOrder") Integer idOrder);
}
