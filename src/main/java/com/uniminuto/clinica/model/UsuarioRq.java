package com.uniminuto.clinica.model;

import lombok.Data;

/**
 *
 * @author lmora
 */
@Data
public class UsuarioRq {
    private Long id;
    private String username;
    private String password;
    private String rol;
    private boolean activo;
    private String email;
}
