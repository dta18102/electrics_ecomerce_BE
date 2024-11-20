package com.example.medimateserver.repository;

import com.example.medimateserver.entity.Role;
import com.example.medimateserver.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Object> findByIdUser(Integer id);
}
