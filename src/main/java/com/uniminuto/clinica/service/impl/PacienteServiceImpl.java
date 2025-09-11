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

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public List<Paciente> listarTodo() {
        return this.pacienteRepository.findAll();
    }

    @Override
    public Paciente buscarPacientePorDocumento(String numeroDocumento)
            throws BadRequestException {
        
        if (numeroDocumento == null || numeroDocumento.trim().isEmpty()) {
            throw new BadRequestException("El número de documento es requerido");
        }

        Optional<Paciente> paciente = this.pacienteRepository
                .findByNumeroDocumento(numeroDocumento.trim());

        if (!paciente.isPresent()) {
            throw new BadRequestException("Paciente con documento " 
                    + numeroDocumento + " no encontrado");
        }

        return paciente.get();
    }
}