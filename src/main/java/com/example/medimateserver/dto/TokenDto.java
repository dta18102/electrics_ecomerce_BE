package com.example.medimateserver.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenDto {
    private Integer idUser;
    private String accessToken;
    private String refreshToken;
}
