package com.uniminuto.clinica.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "auditoria")
public class Auditoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Integer sessionId;

    // Este SÍ puede ser null (la tabla lo permite)
    @Column(name = "user_id")
    private Integer userId;

    // También permite null
    @Column(name = "token", length = 500)
    private String token;

    /** Fecha y hora de inicio de sesión */
    @Column(name = "fecha_ini_sesion")
    private LocalDateTime fechaIniSesion;

    /** Fecha y hora de expiración de la sesión */
    @Column(name = "fecha_expiracion")
    private LocalDateTime fechaExpiracion;

    //Nuevos Campos
    @Column(name = "fecha_evento",nullable = false)
    private LocalDateTime fechaEvento;

    @Column(name="username_ingresado",nullable = false,length = 100)
    private String usernameIngresado;

    @Column(name = "descripcion_error",nullable = false,length = 255)
    private String descripcionError;
}
