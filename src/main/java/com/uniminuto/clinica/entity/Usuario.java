package com.uniminuto.clinica.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author lmora
 */
@Data
@Entity
@Table(name="usuario")
public class Usuario implements Serializable {
    
    /**
     * Id serializable.
     */
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "username")
    private String username;
    
    @Column(name = "password_hash")
    private String password;
    
    @Column(name = "rol")
    private String rol;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @Column(name = "activo")
    private boolean activo;

    @Column(name = "email")
    private String email;
    
}
