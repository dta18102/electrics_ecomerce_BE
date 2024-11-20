package com.example.medimateserver.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeviceDto {
    private Integer idUser;
    private String token;
    private Integer status;
}

