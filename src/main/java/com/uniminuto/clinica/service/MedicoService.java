package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Medico;
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
}
