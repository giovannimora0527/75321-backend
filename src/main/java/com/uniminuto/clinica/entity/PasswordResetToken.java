package com.uniminuto.clinica.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Data;

/**
 *
 * @author jorge
 */

@Data
@Entity
@Table(name = "password_reset_token")
public class PasswordResetToken implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "token", nullable = false, unique = true, length = 500)
    private String token;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    @Column(name = "fecha_expiracion", nullable = false)
    private LocalDateTime fechaExpiracion;
    
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "utilizado", nullable = false)
    private boolean utilizado = false;
    
    public PasswordResetToken() {}
    
    public PasswordResetToken(String token, Usuario usuario, LocalDateTime fechaExpiracion) {
        this.token = token;
        this.usuario = usuario;
        this.fechaExpiracion = fechaExpiracion;
        this.fechaCreacion = LocalDateTime.now();
        this.utilizado = false;
    }
    
    // Método para verificar si el token ha expirado
    public boolean estaExpirado() {
        return LocalDateTime.now().isAfter(this.fechaExpiracion);
    }
}
