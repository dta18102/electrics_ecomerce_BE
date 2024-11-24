package com.example.medimateserver.controller.api;

import com.example.medimateserver.config.jwt.JwtProvider;
import com.example.medimateserver.dto.*;
import com.example.medimateserver.entity.OrderDetail;
import com.example.medimateserver.service.OrderDetailService;
import com.example.medimateserver.service.OrderService;
import com.example.medimateserver.service.TokenService;
import com.example.medimateserver.service.UserService;
import com.example.medimateserver.util.GsonUtil;
import com.example.medimateserver.util.ResponseUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/order_detail", produces = "application/json")
public class OrderDetailController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllOrderDetail(HttpServletRequest request, @PathVariable Integer id) throws JsonProcessingException {
        try {
            String tokenInformation = request.getHeader("Authorization").substring(7);
            UserDto user = GsonUtil.gI().fromJson(JwtProvider.gI().getUsernameFromToken(tokenInformation), UserDto.class);
            List<OrderDetailDto> orderDetailList = orderDetailService.findByIdUser(id);
            String jsons = GsonUtil.gI().toJson(orderDetailList);
            return ResponseUtil.success(jsons);
        } catch (Exception ex) {
            System.out.println("Lỗi ở đây " + ex.getMessage());
            return ResponseUtil.failed();
        }
    }

    @PutMapping
    public ResponseEntity<?> updateFeedback(HttpServletRequest request, @RequestBody OrderDetailDto orderDetailDto) {
        try {
            String tokenInformation = request.getHeader("Authorization").substring(7);
            UserDto user = GsonUtil.gI().fromJson(JwtProvider.gI().getUsernameFromToken(tokenInformation), UserDto.class);
            orderDetailService.update(orderDetailDto);
            return ResponseUtil.success();
        } catch (Exception ex) {
            System.out.println("Lỗi ở đây nè " + ex.getMessage());
            return ResponseUtil.failed();
        }
    }
}
