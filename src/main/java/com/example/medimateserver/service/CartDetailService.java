package com.example.medimateserver.service;

import com.example.medimateserver.dto.CartDetailDto;
import com.example.medimateserver.dto.OrderDetailDto;
import com.example.medimateserver.entity.CartDetail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartDetailService {
    List<CartDetailDto> findAll();
    List<CartDetailDto> findByIdUser(Integer id);
    void saveCartDetail(CartDetailDto cartDetailDto);
    void updateCartDetail(CartDetailDto cartDetailDto);
    void deleteCartDetail(Integer idUser, Integer idProduct);
    void deleteCartDetail(Integer idUser, List<CartDetailDto> cartDetailDto);
}
