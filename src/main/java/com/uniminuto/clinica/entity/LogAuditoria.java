package com.uniminuto.clinica.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "log_auditoria")
public class LogAuditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "username")
    private String username;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "tipo_evento")
    private String tipoEvento; // Ej: LOGIN_FALLIDO, RECUPERACION_CLAVE, BLOQUEO_USUARIO
}