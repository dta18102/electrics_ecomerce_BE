package com.example.medimateserver.repository;

import com.example.medimateserver.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    List<Address> findByIdUser(Integer id);
}
