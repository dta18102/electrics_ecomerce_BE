package com.example.medimateserver.controller.api;

import com.example.medimateserver.config.jwt.JwtProvider;
import com.example.medimateserver.dto.ProvinceDto;
import com.example.medimateserver.dto.TokenDto;
import com.example.medimateserver.dto.UserDto;
import com.example.medimateserver.service.DistrictService;
import com.example.medimateserver.service.TokenService;
import com.example.medimateserver.service.UserService;
import com.example.medimateserver.util.GsonUtil;
import com.example.medimateserver.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/district", produces = "application/json")
public class DistrictController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private DistrictService districtService;
    @GetMapping("/{id}")
    public ResponseEntity<?> getAllDistrict(HttpServletRequest request, @PathVariable Integer id) {
        try {
            String tokenInformation = request.getHeader("Authorization");
            tokenInformation = tokenInformation.substring(7);
            UserDto user = GsonUtil.gI().fromJson(JwtProvider.gI().getUsernameFromToken(tokenInformation), UserDto.class);
            String json = GsonUtil.gI().toJson(districtService.findByIdProvince(id));
            return ResponseUtil.success(json);
        } catch (Exception ex) {
            return ResponseUtil.failed();
        }
    }
}
