package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.model.RespuestaRs;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author lmora
 */
public interface MedicoService {

    List<Medico> listarTodo();

    List<Medico> obtenerMedicosPorEspecializacion(String codigoEspec)
            throws BadRequestException;

    RespuestaRs guardarMedico(Medico medico)
            throws BadRequestException;
    
    RespuestaRs actualizarMedico(Medico medico)
            throws BadRequestException;

    
}
