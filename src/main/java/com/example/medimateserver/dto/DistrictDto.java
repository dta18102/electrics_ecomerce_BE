package com.example.medimateserver.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DistrictDto {
    private Integer id;
    private Integer idProvince;
    private String name;
}
