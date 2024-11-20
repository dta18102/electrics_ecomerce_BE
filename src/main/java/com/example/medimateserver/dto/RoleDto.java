package com.example.medimateserver.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor // Thêm constructor không tham số
public class RoleDto {
    private Integer id;
    private String name;
    private Integer status;
}
