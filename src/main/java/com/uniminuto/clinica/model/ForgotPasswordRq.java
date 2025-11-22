package com.uniminuto.clinica.model;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Email;

@Data
public class ForgotPasswordRq {
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un email válido")
    private String email;
}
