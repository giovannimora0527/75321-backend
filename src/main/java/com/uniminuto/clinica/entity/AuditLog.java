package com.uniminuto.clinica.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.OffsetDateTime;

@Entity
@Table(name = "audit_log")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_ingresado")
    private String usuarioIngresado;

    @Column(name = "evento", nullable = false)
    private String evento;

    @Column(name = "descripcion", columnDefinition = "text")
    private String descripcion;

    @Column(name = "ip")
    private String ip;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "fecha")
    private LocalDateTime fecha = LocalDateTime.now();

    public AuditLog() {
    }

    // getters / setters


    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getUsuarioIngresado() {
        return usuarioIngresado;
    }

    public void setUsuarioIngresado(String usuarioIngresado) {
        this.usuarioIngresado = usuarioIngresado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;

    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;

    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}

