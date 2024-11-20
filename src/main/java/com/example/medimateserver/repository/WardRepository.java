package com.example.medimateserver.repository;

import com.example.medimateserver.entity.Province;
import com.example.medimateserver.entity.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WardRepository extends JpaRepository<Ward, Integer> {
    List<Ward> findByIdDistrict(Integer id);
}
