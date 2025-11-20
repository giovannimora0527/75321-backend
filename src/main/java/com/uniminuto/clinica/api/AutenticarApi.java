package com.uniminuto.clinica.api;

import com.uniminuto.clinica.model.AutenticatorRs;
import com.uniminuto.clinica.model.AuthenticatorRq;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;

@RequestMapping("/auth")
@CrossOrigin(origins = "*") // Importante para que Angular pueda conectarse sin error de CORS
public interface AutenticarApi {

    @PostMapping("/login")
    ResponseEntity<AutenticatorRs> autenticar(@Valid @RequestBody AuthenticatorRq request) throws BadRequestException;

    // --- ESTE ES EL MÉTODO QUE TE FALTABA ---
    @PostMapping("/recuperar-contrasena")
    ResponseEntity<Void> recuperarContrasena(@Valid @RequestBody AuthenticatorRq request);
}