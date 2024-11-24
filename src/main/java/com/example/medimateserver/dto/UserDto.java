package com.example.medimateserver.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;


@Data
@NoArgsConstructor // Thêm constructor không tham số
public class UserDto {
    private Integer id;
    private Integer idRole;
    private String phone;
    private String email;
    private String username;
//    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*-_=<>?]).{8,32}$", message = "Mật khẩu phải bao gồm ít nhất một chữ hoa, một chữ số, một ký tự đặc biệt và dài 8-32 ký tự")
    private String password;
    private String rank;
    private Integer point;
    private Date birthday;
    private Integer gender;
    private String image;
    private String favourite;
    private Integer status;

}
