package com.example.medimateserver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="device")
@Data
@NoArgsConstructor
public class Device {
    @Id
    @Column(name = "id_user")
    private Integer idUser;
    @Column(name = "token")
    private String token;
    @Column(name = "status")
    private Integer status;
}
