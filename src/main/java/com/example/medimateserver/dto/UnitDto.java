package com.example.medimateserver.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UnitDto {
    private Integer id;
    private String name;
    private String description;
    private Integer status;
}
