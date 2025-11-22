package com.uniminuto.clinica.model;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class ResetPasswordRq {
    
    @NotBlank(message = "El token es obligatorio")
    private String token;
    
    @NotBlank(message = "La nueva contraseña es obligatoria")
    private String newPassword;
    
    @NotBlank(message = "La confirmación de contraseña es obligatoria")
    private String confirmPassword;
}
