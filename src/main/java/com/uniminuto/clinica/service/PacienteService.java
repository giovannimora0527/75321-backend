package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.entity.Paciente;

import java.util.List;

import org.apache.coyote.BadRequestException;

/**
 *
 * @author lmora
 */
public interface PacienteService {
    List<Paciente> listarTodo();

    List<Paciente> obtenerPacientePorDocumento(String numeroDocumento)
            throws BadRequestException;

    List<Paciente> listarPacientesMayorAMenor();
}
