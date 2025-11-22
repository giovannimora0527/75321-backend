package com.uniminuto.clinica.security;

import com.uniminuto.clinica.service.AuditoriaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEventListener {
    
    private static final Logger log = LoggerFactory.getLogger(AuthenticationEventListener.class);
    
    @Autowired
    private AuditoriaService auditoriaService;
    
    @EventListener
    public void onAuthenticationFailure(AuthenticationFailureBadCredentialsEvent event) {
        String nombreUsuario = event.getAuthentication().getName();
        String error = "Credenciales inválidas - " + event.getException().getMessage();
        
        auditoriaService.registrarLoginFallido(nombreUsuario, error);
        
        log.error(" Intento de login fallido para usuario: {}", nombreUsuario);
    }
    
    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        String nombreUsuario = event.getAuthentication().getName();
        
        // Reiniciar contador de intentos fallidos
        auditoriaService.reiniciarIntentosUsuario(nombreUsuario);
        
        log.info(" Login exitoso para usuario: {}", nombreUsuario);
    }
}
