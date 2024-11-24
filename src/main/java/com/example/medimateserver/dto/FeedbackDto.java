package com.example.medimateserver.dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FeedbackDto {
    private Integer id;
    private Integer idUser;
    private UserDto user;
    private Integer idProduct;
    private ProductDto product;
    private Integer star;
    private String description;
    private String reply;
}
