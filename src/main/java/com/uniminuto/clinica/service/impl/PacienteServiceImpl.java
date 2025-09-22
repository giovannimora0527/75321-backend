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
 * @author AORUS
 */
@Service
public class PacienteServiceImpl implements PacienteService{
    @Autowired
    private PacienteRepository pacienteRepository;


    @Override
    public List<Paciente> listarTodo() {
        return this.pacienteRepository.findAll();
    }
    
    @Override
    public Paciente buscarPacientePorDocumento(String numDoc)
            throws BadRequestException {
        Optional<Paciente> optPac = this.pacienteRepository
                .findByNumDoc(numDoc);
        if (!optPac.isPresent()) {
            throw new BadRequestException("No existe paciente");
        }
        return optPac.get();
    }
    
    // Nuevo método implementado
    @Override
    public List<Paciente> listarPacientesOrdenadosPorFechaNacimiento() {
        return this.pacienteRepository.findAllByOrderByFechaNacimientoDesc();
    }
}
