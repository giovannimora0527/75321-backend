package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.model.PacienteRq;
import com.uniminuto.clinica.model.RespuestaRs;

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

    RespuestaRs guardarPaciente(PacienteRq pacienteRq) throws BadRequestException;

    RespuestaRs actualizarPaciente(PacienteRq pacienteRq) throws BadRequestException;
}
