package com.example.medimateserver.service.impl;

import com.example.medimateserver.dto.*;
import com.example.medimateserver.dto.TokenDto;
import com.example.medimateserver.entity.Address;
import com.example.medimateserver.entity.Feedback;
import com.example.medimateserver.entity.Product;
import com.example.medimateserver.entity.Token;
import com.example.medimateserver.repository.FeedbackRepository;
import com.example.medimateserver.repository.TokenRepository;
import com.example.medimateserver.service.FeedbackService;
import com.example.medimateserver.service.TokenService;
import com.example.medimateserver.util.ConvertUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository; // Sử dụng private cho đúng nguyên tắc

    @Override
    public List<FeedbackDto> findAll() {
        List<Feedback> feedbackList = feedbackRepository.findAll();
        return feedbackList
                .stream()
                .map(Feedback -> ConvertUtil.gI().toDto(Feedback, FeedbackDto.class))
                .collect(Collectors.toList());
    }

//    @Override
//    public FeedbackDto findByIdUserAndIdProduct(Integer idUser, Integer idProduct) {
//        return feedbackRepository.findByIdUserAndIdProduct(idUser, idProduct)
//                .map(Feedback -> ConvertUtil.gI().toDto(Feedback, FeedbackDto.class))
//                .orElse(null);
//    }

    @Override
    public FeedbackDto findById(Integer id) {

        return feedbackRepository.findById(id)
                .map(Feedback -> ConvertUtil.gI().toDto(Feedback, FeedbackDto.class))
                .orElse(null);
    }
    @Override
    public  List<FeedbackDto> findByIdUser(Integer id) {

        List<Feedback> addressList = feedbackRepository.findByIdUser(id);
        return addressList
                .stream()
                .map(feedback -> ConvertUtil.gI().toDto(feedback, FeedbackDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedbackDto> findByIdProduct(Integer id) {
        // Lấy tất cả Feedback từ repository
        List<Feedback> feedbackList = feedbackRepository.findAll();
        // Lọc danh sách theo idProduct
        return feedbackList.stream()
                .filter(feedback -> feedback.getProduct().getId().equals(id)) // Lọc theo idProduct
                .map(feedback -> ConvertUtil.gI().toDto(feedback, FeedbackDto.class)) // Chuyển đổi sang FeedbackDto
                .collect(Collectors.toList());
    }



    @Override
    public  FeedbackDto save(FeedbackDto feedbackDto) {
        Feedback feedback = ConvertUtil.gI().toEntity(feedbackDto, Feedback.class);
        feedback = feedbackRepository.save(feedback);
        return ConvertUtil.gI().toDto(feedback, FeedbackDto.class);
    }

    @Override
    public FeedbackDto update( FeedbackDto feedbackDto) {
        Feedback existingFeedback = feedbackRepository.findById(feedbackDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Feedback not found!"));
        if(feedbackDto.getDescription() != null){
            existingFeedback.setDescription(feedbackDto.getDescription());
        }
        if(feedbackDto.getStar() != null){
            existingFeedback.setStar(feedbackDto.getStar());
        }
        if(feedbackDto.getReply() != null){
            existingFeedback.setReply(feedbackDto.getReply());
        }
        Feedback updatedFeedback = feedbackRepository.save(existingFeedback);
        return ConvertUtil.gI().toDto(updatedFeedback, FeedbackDto.class);
    }

}

