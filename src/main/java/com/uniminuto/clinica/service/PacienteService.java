package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Paciente;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface PacienteService {

    List<Paciente> listarTodo();

    Paciente buscarPacientePorDocumento(String numDocumento)
        throws BadRequestException;

    List<Paciente> PacientesPorFechaDeNacimiento();
}
