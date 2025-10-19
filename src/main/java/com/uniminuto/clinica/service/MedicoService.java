package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Medico;
import java.util.List;

import com.uniminuto.clinica.model.MedicoRq;
import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author lmora
 */
public interface MedicoService {

    List<Medico> listarTodo();

    List<Medico> obtenerMedicosPorEspecializacion(String codigoEspec)
            throws BadRequestException;


    Medico guardar(Medico medico)
            throws BadRequestException;

    RespuestaRs actualizarMedico(MedicoRq medicoRq) throws BadRequestException;
}
