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
 * Entidad que representa un usuario del sistema de clínica.
 *
 * @author lmora
 */
@Data
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    /**
     * ID de serialización para la clase.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador único del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Nombre de usuario para el login.
     */
    @Column(name = "username")
    private String username;

    /**
     * Contraseña encriptada del usuario.
     */
    @Column(name = "password_hash")
    private String password;

    /**
     * Rol del usuario en el sistema.
     */
    @Column(name = "rol")
    private String rol;

    /**
     * Fecha de creación de la cuenta.
     */
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    /**
     * Estado activo/inactivo del usuario.
     */
    @Column(name = "activo")
    private boolean activo;

    /**
     * Número de documento único del usuario.
     */
    @Column(unique = true)
    private String numeroDocumento;
}