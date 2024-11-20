package com.example.medimateserver.service;

import com.example.medimateserver.dto.OrderDetailDto;
import com.example.medimateserver.dto.OrderDto;
import com.example.medimateserver.dto.PaymentDto;
import com.example.medimateserver.entity.OrderDetail;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface OrderService {
    List<OrderDto> findAll();
    OrderDto findById(Integer id);
    OrderDto save(PaymentDto paymentDto);
    List<OrderDto> findByIdUser(Integer id);
    OrderDto saveWithOrderDetail(PaymentDto paymentDto);
    OrderDto confirmOrder(Integer id);
}
