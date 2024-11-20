package com.example.medimateserver.service.impl;

import com.example.medimateserver.dto.OrderDetailDto;
import com.example.medimateserver.dto.OrderDto;
import com.example.medimateserver.dto.ProductDto;
import com.example.medimateserver.entity.OrderDetail;
import com.example.medimateserver.entity.Orders;
import com.example.medimateserver.repository.OrderDetailRepository;
import com.example.medimateserver.service.OrderDetailService;
import com.example.medimateserver.util.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Override
    public List<OrderDetailDto> findAll() {
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();
        return orderDetails.stream()
                .map(this::toDto) // Sử dụng hàm toDto trong cùng lớp
                .collect(Collectors.toList());
    }
    public List<OrderDetailDto> findByIdUser(Integer id) {
        List<OrderDetail> orderDetails = orderDetailRepository.findByIdOrder(id);
        return orderDetails.stream()
                .map(this::toDto) // Sử dụng hàm toDto trong cùng lớp
                .collect(Collectors.toList());
    }


    public OrderDetailDto toDto(OrderDetail orderDetail) {
        OrderDetailDto dto = new OrderDetailDto();
        dto.setIdOrder(orderDetail.getId().getIdOrder());
        dto.setIdProduct(orderDetail.getId().getIdProduct());
        dto.setDiscountPrice(orderDetail.getDiscountPrice());
        dto.setQuantity(orderDetail.getQuantity());
        dto.setProduct(ConvertUtil.gI().toDto(orderDetail.getProduct(), ProductDto.class));
        dto.setOrders(ConvertUtil.gI().toDto(orderDetail.getOrders(), OrderDto.class));
        return dto;
    }

    public OrderDetail toEntity(OrderDetailDto dto) {
        OrderDetail.OrderDetailId id = new OrderDetail.OrderDetailId(dto.getIdOrder(), dto.getIdProduct()); // Cách 2, sau khi thêm static
        return new OrderDetail(id, dto.getProductPrice(), dto.getDiscountPrice(), dto.getQuantity());
    }

}
