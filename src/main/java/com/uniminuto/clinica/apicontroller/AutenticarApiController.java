package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.AutenticarApi;
import com.uniminuto.clinica.model.AutenticatorRs;
import com.uniminuto.clinica.model.AuthenticatorRq;
import com.uniminuto.clinica.service.AutenticarService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth") // Asegurar prefijo si no está en properties
public class AutenticarApiController implements AutenticarApi {

    @Autowired
    private AutenticarService autenticarService;

    @Autowired
    private HttpServletRequest httpRequest;

    @Override
    @PostMapping("/login") // Verificar mapeo en AutenticarApi
    public ResponseEntity<AutenticatorRs> autenticar(@RequestBody AuthenticatorRq request) throws BadRequestException {
        return ResponseEntity.ok(this.autenticarService.autenticar(request));
    }

    // NUEVO ENDPOINT PARA RECUPERACIÓN
    @PostMapping("/recuperar-contrasena")
    public ResponseEntity<Void> recuperarContrasena(@RequestBody AuthenticatorRq request) {
        String ip = httpRequest.getRemoteAddr();
        // Solo necesitamos el username del request
        autenticarService.recuperarContrasena(request.getUsername(), ip);
        return ResponseEntity.ok().build();
    }
}