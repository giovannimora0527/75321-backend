package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Paciente;
import java.util.List;

import com.uniminuto.clinica.repository.PacienteRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 *
 * @author lmora
 */
public interface PacienteService {
    List<Paciente> listarTodo();
    Paciente buscarPacientePorDocumento(String numeroDocumento) throws BadRequestException;
    List<Paciente> listarPacientesPorFechaNacimientoDesc();
}






