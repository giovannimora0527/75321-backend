package com.uniminuto.clinica.service;

import com.uniminuto.clinica.model.AuditoriaRs;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AuditoriaService {
    // Para errores (usuario no existe)

    // Para errores de recuperación
    void registrarError(String username, String descripcionError);

    // Para recuperación exitosa
    void registrarRecuperacion(Integer userId, String username);

    //Firma del servicio de Auditoria +Angular
   List<AuditoriaRs>filtrar(String username,String tipo,String desde, String hasta);


}
