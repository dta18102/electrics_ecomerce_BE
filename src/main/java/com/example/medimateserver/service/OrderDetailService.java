package com.example.medimateserver.service;

import com.example.medimateserver.dto.OrderDetailDto;
import com.example.medimateserver.entity.OrderDetail;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface OrderDetailService {
    List<OrderDetailDto> findAll();
    List<OrderDetailDto> findByIdUser(Integer id);
    void update(OrderDetailDto orderDetailDto);
}
