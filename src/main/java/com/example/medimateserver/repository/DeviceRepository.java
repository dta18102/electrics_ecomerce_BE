package com.example.medimateserver.repository;

import com.example.medimateserver.entity.Device;
import com.example.medimateserver.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {
}
