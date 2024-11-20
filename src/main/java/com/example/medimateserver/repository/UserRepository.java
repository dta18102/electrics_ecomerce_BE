package com.example.medimateserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.medimateserver.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByPhone(String phone);
    Optional<User> findByEmail(String email);
}
