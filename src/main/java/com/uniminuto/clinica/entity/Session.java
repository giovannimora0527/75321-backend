package com.uniminuto.clinica.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Entidad que representa una sesión de usuario.
 */
@Data
@Entity
@Table(name = "session")
public class Session implements Serializable {

    /** Identificador único de la sesión */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Integer sessionId;

    /** Identificador del usuario asociado a la sesión */
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    /** Token JWT de la sesión */
    @Column(name = "token", nullable = false, length = 500)
    private String token;

    /** Fecha y hora de inicio de sesión */
    @Column(name = "fecha_ini_sesion")
    private LocalDateTime fechaIniSesion;

    /** Fecha y hora de expiración de la sesión */
    @Column(name = "fecha_expiracion")
    private LocalDateTime fechaExpiracion;

}
