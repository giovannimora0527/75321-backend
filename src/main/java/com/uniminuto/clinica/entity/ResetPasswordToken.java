package com.uniminuto.clinica.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


import java.time.OffsetDateTime;



import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "reset_password_token")
public class ResetPasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id", nullable = false)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(name="fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name="fecha_expiracion")
    private LocalDateTime fechaExpiracion;

    @Column(nullable = false)
    private boolean usado = false;
}
