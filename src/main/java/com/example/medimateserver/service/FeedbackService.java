package com.example.medimateserver.service;

import com.example.medimateserver.dto.AddressDto;
import com.example.medimateserver.dto.FeedbackDto;
import com.example.medimateserver.dto.ProductDto;
import com.example.medimateserver.dto.TokenDto;
import com.example.medimateserver.entity.Feedback;

import java.util.List;

public interface FeedbackService {
    List<FeedbackDto> findAll();
    //FeedbackDto findByIdUserAndIdProduct(Integer idUser, Integer idProduct);
    List<FeedbackDto> findByIdUser(Integer id);
    List<FeedbackDto> findByIdProduct(Integer id);
    FeedbackDto save(FeedbackDto feedbackDto);

    FeedbackDto findById(Integer id);

    FeedbackDto update( FeedbackDto feedbackDto);
}
