package com.uniminuto.clinica.model;

import lombok.Data;

@Data
public class ResetPasswordRs {
    
    private boolean exitoso;
    private String mensaje;
    private String token;
    
    public ResetPasswordRs() {}
    
    public ResetPasswordRs(boolean exitoso, String mensaje) {
        this.exitoso = exitoso;
        this.mensaje = mensaje;
    }
    
    public ResetPasswordRs(boolean exitoso, String mensaje, String token) {
        this.exitoso = exitoso;
        this.mensaje = mensaje;
        this.token = token;
    }
}
