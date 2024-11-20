package com.example.medimateserver.repository;

import com.example.medimateserver.entity.CouponDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponDetailRepository extends JpaRepository<CouponDetail, Integer> {

    List<CouponDetail> findByIdUser(Integer id);
}
