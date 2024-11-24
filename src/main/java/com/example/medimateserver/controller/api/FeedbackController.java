package com.example.medimateserver.controller.api;

import com.example.medimateserver.config.jwt.JwtProvider;
import com.example.medimateserver.dto.*;
import com.example.medimateserver.service.AddressService;
import com.example.medimateserver.service.FeedbackService;
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
@RequestMapping(value = "/api/feedback", produces = "application/json")
public class FeedbackController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private FeedbackService feedbackService;

    @GetMapping
    public ResponseEntity<?> getAllFeedbackByUser(HttpServletRequest request) throws JsonProcessingException {
        try {
            String tokenInformation = request.getHeader("Authorization").substring(7);
            UserDto user = GsonUtil.gI().fromJson(JwtProvider.gI().getUsernameFromToken(tokenInformation), UserDto.class);
            if(user.getIdRole() == 1){ //admin
                List<FeedbackDto> feedbackList = feedbackService.findAll();
                String jsons = GsonUtil.gI().toJson(feedbackList);
                return ResponseUtil.success(jsons);
            }
            else {  //user
                List<FeedbackDto> feedbackList = feedbackService.findByIdUser(user.getId());
                String jsons = GsonUtil.gI().toJson(feedbackList);
                return ResponseUtil.success(jsons);
            }

        } catch (Exception ex) {
            return ResponseUtil.failed();
        }
    }

    @GetMapping("/product/{idProduct}")
    public ResponseEntity<?> getAllFeedbackByProduct(HttpServletRequest request,@PathVariable Integer idProduct) throws JsonProcessingException {
        try {
            List<FeedbackDto> feedbackList = feedbackService.findByIdProduct(idProduct);
            String jsons = GsonUtil.gI().toJson(feedbackList);
            return ResponseUtil.success(jsons);
        } catch (Exception ex) {
            return ResponseUtil.failed();
        }
    }

    // Create a new Address
    @PostMapping
    public ResponseEntity<?> createFeedback(HttpServletRequest request, @RequestBody FeedbackDto feedbackDto) {
        try {
            System.out.println(feedbackDto.toString());
            String tokenInformation = request.getHeader("Authorization").substring(7);
            UserDto user = GsonUtil.gI().fromJson(JwtProvider.gI().getUsernameFromToken(tokenInformation), UserDto.class);
            feedbackDto.setIdUser(user.getId());
            FeedbackDto savedFeedback = feedbackService.save( feedbackDto);
            return ResponseUtil.success(GsonUtil.gI().toJson(savedFeedback));
        } catch (Exception ex) {
            System.out.println("Lỗi ở đây " + ex.getMessage());
            return ResponseUtil.failed();
        }
    }

    @PutMapping
    public ResponseEntity<?> replyFeedback(HttpServletRequest request, @RequestBody FeedbackDto feedbackDto) {
        try {
            String tokenInformation = request.getHeader("Authorization").substring(7);
            UserDto user = GsonUtil.gI().fromJson(JwtProvider.gI().getUsernameFromToken(tokenInformation), UserDto.class);
            if(user.getIdRole() != 1){ // just admin can reply feedback
                return ResponseUtil.failed(400, "Permission denied!");
            }
            FeedbackDto savedFeedback = feedbackService.update(feedbackDto);
            return ResponseUtil.success(GsonUtil.gI().toJson(savedFeedback));
        } catch (Exception ex) {
            System.out.println("Lỗi ở đây " + ex.getMessage());
            return ResponseUtil.failed();
        }
    }

}
