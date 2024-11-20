package com.example.medimateserver.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "token")
@Data
@NoArgsConstructor // Thêm constructor không tham số
public class Token {
    @Id
    @Column(name = "id_user")
    private Integer idUser;
    @Column(name = "access_token")
    private String accessToken;
    @Column(name = "refresh_token")
    private String refreshToken;
}
