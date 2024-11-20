package com.example.medimateserver.repository;

import com.example.medimateserver.entity.Address;
import com.example.medimateserver.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    @Query("SELECT n FROM Notification n WHERE n.idUser IS NULL OR n.idUser = :userId ORDER BY n.createTime DESC")
    List<Notification> findAllByIdUser(@Param("userId") Integer userId);
}
