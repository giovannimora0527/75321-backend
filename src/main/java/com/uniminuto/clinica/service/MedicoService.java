package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Medico;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 * Servicio para la lógica de negocio de médicos.
 *
 * @author lmora
 */
public interface MedicoService {

    /**
     * Lista todos los médicos registrados.
     *
     * @return lista de médicos
     */
    List<Medico> listarTodo();

    /**
     * Obtiene médicos por código de especialización.
     *
     * @param codigoEspec código de la especialización
     * @return lista de médicos de la especialización
     * @throws BadRequestException si el código es inválido
     */
    List<Medico> obtenerMedicosPorEspecializacion(String codigoEspec)
            throws BadRequestException;
}