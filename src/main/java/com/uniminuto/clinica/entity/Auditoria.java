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
