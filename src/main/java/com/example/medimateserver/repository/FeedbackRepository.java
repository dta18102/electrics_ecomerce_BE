package com.example.medimateserver.repository;

import com.example.medimateserver.dto.FeedbackDto;
import com.example.medimateserver.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    //Optional<Object> findByIdUserAndIdProduct(Integer idUser, Integer idProduct);
    List<Feedback> findByIdUser(Integer id);

}
