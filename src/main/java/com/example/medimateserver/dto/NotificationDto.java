package com.example.medimateserver.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class NotificationDto {
    private Integer id;
    private Integer idUser;
    private String title;
    private String content;
    private Date createTime;
    private String image;
    private Integer status;
}
