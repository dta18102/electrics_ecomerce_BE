package com.example.medimateserver.repository;

import com.example.medimateserver.entity.Category;
import com.example.medimateserver.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Integer> {
}
