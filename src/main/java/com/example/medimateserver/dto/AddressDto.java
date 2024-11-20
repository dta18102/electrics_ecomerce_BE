package com.example.medimateserver.dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressDto {
    private Integer id;
    private Integer idUser;
    private String fullName;
    private String phone;
    private String ward;
    private String district;
    private String province;
    private String type;
    private Boolean isDefault;
    private String specificAddress;
    private Integer status;

}
