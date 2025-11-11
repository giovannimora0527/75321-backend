package com.uniminuto.clinica.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UsuarioRq {
    private String username;
    private String password;
    private String rol;
    private Boolean activo;

    @Email(message = "Email no válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

}
