package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.service.PacienteService;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio para la lógica de negocio de pacientes.
 * Proporciona operaciones de consulta y búsqueda de pacientes.
 *
 * @author lmora
 */
@Service
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;

    /**
     * Constructor que inicializa las dependencias del servicio.
     *
     * @param pacienteRepository repositorio para operaciones de pacientes
     */
    public PacienteServiceImpl(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    /**
     * Lista todos los pacientes registrados en el sistema.
     * No aplica filtros ni criterios de ordenamiento específicos.
     *
     * @return lista completa de pacientes registrados, vacía si no hay pacientes
     */
    @Override
    public List<Paciente> listarTodo() {
        return pacienteRepository.findAll();
    }

    /**
     * Busca un paciente específico por su número de documento.
     * Lanza excepción si no encuentra el paciente.
     *
     * @param numeroDocumento número de documento del paciente a buscar
     * @return paciente encontrado con el documento especificado
     * @throws BadRequestException si no se encuentra paciente con ese documento
     */
    @Override
    public Paciente buscarPacientePorDocumento(String numeroDocumento) throws BadRequestException {
        return pacienteRepository.findByNumeroDocumento(numeroDocumento)
                .orElseThrow(() -> new BadRequestException("Paciente no encontrado"));
    }

    /**
     * Lista pacientes ordenados por fecha de nacimiento de manera descendente.
     * Los pacientes más jóvenes aparecen primero en la lista.
     *
     * @return lista de pacientes ordenados por fecha de nacimiento descendente, vacía si no hay pacientes
     */
    @Override
    public List<Paciente> listarPacientesPorFechaNacimientoDesc() {
        return pacienteRepository.findAllByOrderByFechaNacimientoDesc();
    }
}