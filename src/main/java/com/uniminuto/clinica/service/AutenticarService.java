package com.uniminuto.clinica.service;

import com.uniminuto.clinica.model.AutenticatorRs;
import com.uniminuto.clinica.model.AuthenticatorRq;
import org.apache.coyote.BadRequestException;

public interface AutenticarService {
    AutenticatorRs autenticar(AuthenticatorRq request) throws BadRequestException;

    // Nuevo método agregado
    void recuperarContrasena(String username, String ipAddress);
}