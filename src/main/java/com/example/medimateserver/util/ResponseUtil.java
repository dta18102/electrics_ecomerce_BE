package com.example.medimateserver.util;

import com.example.medimateserver.dto.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {

    public static ResponseEntity<?> success() {
        ResponseDto.gI().setMessage(HttpStatus.OK.getReasonPhrase());
        ResponseDto.gI().setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(GsonUtil.gI().toJson(ResponseDto.gI()), HttpStatus.OK);
    }
    public static ResponseEntity<?> success(String mess) {
        return new ResponseEntity<>(mess, HttpStatus.OK);
    }

    public static ResponseEntity<?> successLink(String mess) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> jsonData = new HashMap<>();
        jsonData.put("urlPayment", mess); // Thay thế bằng URL thực tế
        return new ResponseEntity<>(jsonData, HttpStatus.OK);
    }

    public static ResponseEntity<?> failed() {
        ResponseDto.gI().setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
        ResponseDto.gI().setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(GsonUtil.gI().toJson(ResponseDto.gI()), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<?> failedExpriration() {
        ResponseDto.gI().setMessage(HttpStatus.UNAUTHORIZED.getReasonPhrase() + " token is expriration");
        ResponseDto.gI().setStatus(HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(GsonUtil.gI().toJson(ResponseDto.gI()), HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<?> failed(int value) {
        ResponseDto.gI().setStatus(value);
        ResponseDto.gI().setMessage(HttpStatus.valueOf(value).getReasonPhrase());
        return new ResponseEntity<>(GsonUtil.gI().toJson(ResponseDto.gI()), HttpStatusCode.valueOf(value));
    }

    public static ResponseEntity<?> failed(int statusCode, String message) {
        ResponseDto.gI().setStatus(statusCode);
        ResponseDto.gI().setMessage(message);
        return new ResponseEntity<>(GsonUtil.gI().toJson(ResponseDto.gI()), HttpStatusCode.valueOf(statusCode));
    }
}
