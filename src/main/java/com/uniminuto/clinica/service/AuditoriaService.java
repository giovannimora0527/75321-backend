package com.uniminuto.clinica.service;

import org.apache.coyote.BadRequestException;

public interface AuditoriaService {
    // Para errores (usuario no existe)

    // Para errores de recuperación
    void registrarError(String username, String descripcionError);

    // Para recuperación exitosa
    void registrarRecuperacion(Integer userId, String username);
}
