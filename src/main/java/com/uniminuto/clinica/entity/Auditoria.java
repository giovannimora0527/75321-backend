package com.uniminuto.clinica.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "auditoria")
public class Auditoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;
    
    @Column(name = "nombre_usuario", nullable = false, length = 50)
    private String nombreUsuario;
    
    @Column(name = "descripcion_error", nullable = false, columnDefinition = "TEXT")
    private String descripcionError;
    
    @Column(name = "direccion_ip", length = 45)
    private String direccionIp;
    
    @Column(name = "bloqueado")
    private Boolean bloqueado = false;
    
    // Constructor vacío
    public Auditoria() {
    }
    
    // Constructor con parámetros
    public Auditoria(String nombreUsuario, String descripcionError, String direccionIp) {
        this.fechaHora = LocalDateTime.now();
        this.nombreUsuario = nombreUsuario;
        this.descripcionError = descripcionError;
        this.direccionIp = direccionIp;
        this.bloqueado = false;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
    
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    public String getDescripcionError() {
        return descripcionError;
    }
    
    public void setDescripcionError(String descripcionError) {
        this.descripcionError = descripcionError;
    }
    
    public String getDireccionIp() {
        return direccionIp;
    }
    
    public void setDireccionIp(String direccionIp) {
        this.direccionIp = direccionIp;
    }
    
    public Boolean getBloqueado() {
        return bloqueado;
    }
    
    public void setBloqueado(Boolean bloqueado) {
        this.bloqueado = bloqueado;
    }
}
