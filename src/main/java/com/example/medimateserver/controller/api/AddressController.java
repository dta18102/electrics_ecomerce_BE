package com.example.medimateserver.controller.api;

import com.example.medimateserver.config.jwt.JwtProvider;
import com.example.medimateserver.dto.AddressDto;
import com.example.medimateserver.dto.ResponseDto;
import com.example.medimateserver.dto.TokenDto;
import com.example.medimateserver.dto.UserDto;
import com.example.medimateserver.service.AddressService;
import com.example.medimateserver.service.TokenService;
import com.example.medimateserver.service.UserService;
import com.example.medimateserver.util.GsonUtil;
import com.example.medimateserver.util.ResponseUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(value = "/api/address", produces = "application/json")
public class AddressController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AddressService addressService;

    @GetMapping
    public ResponseEntity<?> getAllAddress(HttpServletRequest request) throws JsonProcessingException {
        try {
            String tokenInformation = request.getHeader("Authorization").substring(7);
            UserDto user = GsonUtil.gI().fromJson(JwtProvider.gI().getUsernameFromToken(tokenInformation), UserDto.class);
            List<AddressDto> addressList = addressService.findByIdUser(user.getId());
            String jsons = GsonUtil.gI().toJson(addressList);
            return ResponseUtil.success(jsons);
        } catch (Exception ex) {
            return ResponseUtil.failed();
        }
    }

    // Create a new Address
    @PostMapping
    public ResponseEntity<?> createAddress(HttpServletRequest request, @RequestBody AddressDto AddressDto) {
        try {
            String tokenInformation = request.getHeader("Authorization").substring(7);
            UserDto user = GsonUtil.gI().fromJson(JwtProvider.gI().getUsernameFromToken(tokenInformation), UserDto.class);
            AddressDto.setIdUser(user.getId());
            AddressDto.setIsDefault(false);
            AddressDto savedAddress = addressService.save(user.getId(), AddressDto);
            return ResponseUtil.success(GsonUtil.gI().toJson(savedAddress));
        } catch (Exception ex) {
            System.out.println("Lỗi ở đây " + ex.getMessage());
            return ResponseUtil.failed();
        }
    }

    // Update a Address
    @PutMapping
    public ResponseEntity<?> updateAddress(HttpServletRequest request, @RequestBody AddressDto addressDto) {
        try {
            String tokenInformation = request.getHeader("Authorization").substring(7);
            UserDto user = GsonUtil.gI().fromJson(JwtProvider.gI().getUsernameFromToken(tokenInformation), UserDto.class);
            if(addressDto.getIsDefault()){
                AddressDto lastDefaultAddress = addressService.findByIsDefaultTrue(addressDto.getIdUser());
                lastDefaultAddress.setIsDefault(false);
                addressService.update(user.getId(), lastDefaultAddress);
            }
            AddressDto updateDto = addressService.update(user.getId(), addressDto);
            return ResponseUtil.success(GsonUtil.gI().toJson(updateDto));
        } catch (Exception ex) {
            System.out.println("Lỗi ở đây nè " + ex.getMessage());
            return ResponseUtil.failed();
        }
    }

    // Delete a Address
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddress(HttpServletRequest request, @PathVariable Integer id) {
        try {
            String tokenInformation = request.getHeader("Authorization").substring(7);
            UserDto user = GsonUtil.gI().fromJson(JwtProvider.gI().getUsernameFromToken(tokenInformation), UserDto.class);
            addressService.deleteById(id);
            return ResponseUtil.success();
        } catch (Exception ex) {
            return ResponseUtil.failed();
        }
    }
}
