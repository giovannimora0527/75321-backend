package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.AutenticarApi;
import com.uniminuto.clinica.model.AutenticatorRs;
import com.uniminuto.clinica.model.AuthenticatorRq;
import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.repository.UsuarioRepository;
import com.uniminuto.clinica.service.AutenticarService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AutenticarApiController implements AutenticarApi {

    @Autowired
    private AutenticarService autenticarService;


    @Override
    public ResponseEntity<AutenticatorRs> autenticar(AuthenticatorRq request) throws BadRequestException {
        return ResponseEntity.ok(this.autenticarService.autenticar(request));
    }
}

