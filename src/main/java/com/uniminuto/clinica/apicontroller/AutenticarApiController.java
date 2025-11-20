package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.AutenticarApi;
import com.uniminuto.clinica.model.AutenticatorRs;
import com.uniminuto.clinica.model.AuthenticatorRq;
import com.uniminuto.clinica.service.AutenticarService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AutenticarApiController implements AutenticarApi {

    @Autowired
    private AutenticarService autenticarService;

    // Inyectamos HttpServletRequest para poder capturar la IP del usuario (Requerimiento 2)
    @Autowired
    private HttpServletRequest httpRequest;

    @Override
    public ResponseEntity<AutenticatorRs> autenticar(AuthenticatorRq request) throws BadRequestException {
        // Pasamos solo el request, el servicio ya se encarga de la lógica
        // Nota: Si modificaste autenticar() en el servicio para pedir IP, pásala aquí.
        // Si no, déjalo como estaba. Asumiré la versión estándar:
        return ResponseEntity.ok(this.autenticarService.autenticar(request));
    }

    @Override
    public ResponseEntity<Void> recuperarContrasena(AuthenticatorRq request) {
        // Capturamos la IP desde donde se hace la petición
        String ipAddress = httpRequest.getRemoteAddr();

        // Llamamos al servicio pasando el usuario y la IP para la auditoría
        this.autenticarService.recuperarContrasena(request.getUsername(), ipAddress);

        // Retornamos 200 OK (Void)
        return ResponseEntity.ok().build();
    }
}