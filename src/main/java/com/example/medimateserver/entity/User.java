package com.example.medimateserver.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.el.stream.Optional;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor // Thêm constructor không tham số
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "id_role")
    private Integer idRole;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "username")
    private String username;
    @Column(name = "rank")
    private String rank;
    @Column(name = "point")
    private Integer point;
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "gender")
    private Integer gender;
    @Column(name = "image")
    private String image;
    @Column(name = "favourite")
    private String favourite;
    @Column(name = "status")
    private Integer status;
    // Không cần constructor có tham số, Lombok sẽ tự động tạo ra
}
