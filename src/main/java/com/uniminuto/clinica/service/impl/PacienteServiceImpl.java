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
 *
 * @author lmora
 */

@Service
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteServiceImpl(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public List<Paciente> listarTodo() {
        return pacienteRepository.findAll();
    }

    @Override
    public Paciente buscarPacientePorDocumento(String numeroDocumento) throws BadRequestException {
        return pacienteRepository.findByNumeroDocumento(numeroDocumento)
                .orElseThrow(() -> new BadRequestException("Paciente no encontrado"));
    }

    @Override
    public List<Paciente> listarPacientesPorFechaNacimientoDesc() {
        return pacienteRepository.findAllByOrderByFechaNacimientoDesc();
    }
}