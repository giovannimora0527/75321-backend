package com.uniminuto.clinica.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

//Lo que solicitamos
@Data
public class AuthenticatorRq implements Serializable {
    /** Nombre de usuario */
    @NotBlank(message = "El username es obligatorio.")
    private String username;

    /** Contraseña del usuario */
    @NotBlank(message = "El password es obligatorio.")
    private String password;
}
