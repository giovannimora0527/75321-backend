package com.uniminuto.clinica.model;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author lmora
 */
@Data
public class UsuarioRq {

    private Integer id;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    @NotBlank(message = "El rol es obligatorio")
    private String rol;

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activo;
}