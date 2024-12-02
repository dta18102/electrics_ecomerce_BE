package com.example.medimateserver.controller.api;

import com.example.medimateserver.config.jwt.JwtProvider;
import com.example.medimateserver.dto.CartDetailDto;
import com.example.medimateserver.dto.TokenDto;
import com.example.medimateserver.dto.UserDto;
import com.example.medimateserver.entity.CartDetail;
import com.example.medimateserver.service.CartDetailService;
import com.example.medimateserver.service.TokenService;
import com.example.medimateserver.service.UserService;
import com.example.medimateserver.util.GsonUtil;
import com.example.medimateserver.util.ResponseUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/cart_detail", produces = "application/json")
public class CartDetailController {
    @Autowired
    TokenService tokenService;
    @Autowired
    UserService userService;
    @Autowired
    CartDetailService cartDetailService;

    // Lấy tất cả cartDetail dựa vào userID
    @GetMapping
    public ResponseEntity<?> getAllCartDetail(HttpServletRequest request) throws JsonProcessingException {
        try {
            String tokenInformation = request.getHeader("Authorization").substring(7);
            UserDto user = GsonUtil.gI().fromJson(JwtProvider.gI().getUsernameFromToken(tokenInformation), UserDto.class);
            List<CartDetailDto> cartDetailList = cartDetailService.findByIdUser(user.getId());
            String jsons = GsonUtil.gI().toJson(cartDetailList);
            return ResponseUtil.success(jsons);
        } catch (Exception ex) {
            System.out.println("Lỗi ở đây " + ex.getMessage());
            return ResponseUtil.failed();
        }
    }


    @GetMapping("/status/{id}")
    public ResponseEntity<?> getStatusProduct(HttpServletRequest request, @PathVariable Integer id) throws JsonProcessingException {
        try {
            String tokenInformation = request.getHeader("Authorization").substring(7);
            UserDto user = GsonUtil.gI().fromJson(JwtProvider.gI().getUsernameFromToken(tokenInformation), UserDto.class);
            TokenDto tokenDto = tokenService.findById(user.getId());
            if (JwtProvider.gI().verifyToken(tokenInformation, tokenDto)) {
                List<CartDetailDto> cartDetailList = cartDetailService.findByIdUser(user.getId());
                for (CartDetailDto c : cartDetailList) {
                    if (c.getProduct().getId() == id && c.getProduct().getQuantity() - c.getQuantity() >= 0) {
                        return ResponseUtil.success();
                    }
                }
            }
            return ResponseUtil.failed();
        } catch (Exception ex) {
            System.out.println("Lỗi ở đây " + ex.getMessage());
            return ResponseUtil.failed();
        }
    }
    @GetMapping("/total_item")
    public ResponseEntity<?> getTotalItem(HttpServletRequest request) throws JsonProcessingException {
        try {
            String tokenInformation = request.getHeader("Authorization").substring(7);
            UserDto user = GsonUtil.gI().fromJson(JwtProvider.gI().getUsernameFromToken(tokenInformation), UserDto.class);
            TokenDto tokenDto = tokenService.findById(user.getId());
            if (JwtProvider.gI().verifyToken(tokenInformation, tokenDto)) {
                List<CartDetailDto> cartDetailList = cartDetailService.findByIdUser(user.getId());
                String jsons = GsonUtil.gI().toJson(cartDetailList.size());
                return ResponseUtil.success(jsons);
            }
            return ResponseUtil.failed();
        } catch (Exception ex) {
            return ResponseUtil.failed();
        }
    }
    // Tăng số lượng lên 1 hoặc lưu cartDetail với số lượng sản phẩm là 1
    @PostMapping
    public ResponseEntity<?> saveCartDetail(HttpServletRequest request, @RequestBody CartDetailDto cartDetailDto) throws JsonProcessingException {
        try {
            String tokenInformation = request.getHeader("Authorization").substring(7);
            UserDto user = GsonUtil.gI().fromJson(JwtProvider.gI().getUsernameFromToken(tokenInformation), UserDto.class);
            cartDetailDto.setUser(user);
            cartDetailService.saveCartDetail(cartDetailDto);
            return ResponseUtil.success();
        } catch (Exception ex) {
            System.out.println("Lỗi ở đây " + ex.getMessage());
            return ResponseUtil.failed();
        }
    }

    // Tăng sản phẩm cartDetail lên nhiều hoặc xoá nếu số lượng sản phẩm bé hơn 0
    @PutMapping
    public ResponseEntity<?> updateCartDetail(HttpServletRequest request, @RequestBody CartDetailDto cartDetailDto) throws JsonProcessingException {
        try {
            String tokenInformation = request.getHeader("Authorization").substring(7);
            UserDto user = GsonUtil.gI().fromJson(JwtProvider.gI().getUsernameFromToken(tokenInformation), UserDto.class);
            cartDetailDto.setUser(user);
            cartDetailService.updateCartDetail(cartDetailDto);
            return ResponseUtil.success();
        } catch (Exception ex) {
            System.out.println("Lỗi ở đây " + ex.getMessage());
            return ResponseUtil.failed();
        }
    }

    // Xoá sản phẩm ra khỏi cartDetail
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCartDetailA(HttpServletRequest request, @PathVariable Integer id) throws JsonProcessingException {
        try {
            String tokenInformation = request.getHeader("Authorization").substring(7);
            UserDto user = GsonUtil.gI().fromJson(JwtProvider.gI().getUsernameFromToken(tokenInformation), UserDto.class);
            cartDetailService.deleteCartDetail(user.getId(), id);
            return ResponseUtil.success();
        } catch (Exception ex) {
            System.out.println("Lỗi ở đây " + ex.getMessage());
            return ResponseUtil.failed();
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCartDetailB(HttpServletRequest request, @RequestBody List<CartDetailDto> cartDetailDto) throws JsonProcessingException {
        try {
            String tokenInformation = request.getHeader("Authorization").substring(7);
            UserDto user = GsonUtil.gI().fromJson(JwtProvider.gI().getUsernameFromToken(tokenInformation), UserDto.class);
            cartDetailService.deleteCartDetail(user.getId(), cartDetailDto);
            return ResponseUtil.success();
        } catch (Exception ex) {
            System.out.println("Lỗi ở đây " + ex.getMessage());
            return ResponseUtil.failed();
        }
    }
}
