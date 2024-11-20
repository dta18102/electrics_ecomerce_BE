package com.example.medimateserver.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseDto {
    private String message;
    private Integer status;
    private static ResponseDto responseDto;
    public static ResponseDto gI() {
        if (responseDto == null) {
            responseDto = new ResponseDto();
        }
        return responseDto;
    }
}
