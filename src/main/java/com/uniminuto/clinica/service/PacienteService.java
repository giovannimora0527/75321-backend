package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Paciente;
import java.util.List;
import com.uniminuto.clinica.repository.PacienteRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * Servicio para la lógica de negocio de pacientes.
 *
 * @author lmora
 */
public interface PacienteService {

    /**
     * Lista todos los pacientes registrados.
     *
     * @return lista de pacientes
     */
    List<Paciente> listarTodo();

    /**
     * Busca un paciente por número de documento.
     *
     * @param numeroDocumento número de documento del paciente
     * @return paciente encontrado
     * @throws BadRequestException si el documento es inválido
     */
    Paciente buscarPacientePorDocumento(String numeroDocumento) throws BadRequestException;

    /**
     * Lista pacientes ordenados por fecha de nacimiento descendente.
     *
     * @return lista de pacientes ordenados
     */
    List<Paciente> listarPacientesPorFechaNacimientoDesc();
}


