package com.example.medimateserver.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="notification")
@Data
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_user")
    private Integer idUser;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name="create_time")
    private Date createTime;
    @Column(name="image")
    private String image;
    @Column(name = "status")
    private Integer status;
}
