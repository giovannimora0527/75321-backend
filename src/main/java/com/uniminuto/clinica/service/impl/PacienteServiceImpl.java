package com.uniminuto.clinica.service.impl;


import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.repository.PacienteRepository;
import java.util.List;
import com.uniminuto.clinica.service.PacienteService;
import org.springframework.stereotype.Service;


@Service
public class PacienteServiceImpl implements PacienteService {

    private PacienteRepository pacienteRepository;

    public PacienteServiceImpl(PacienteRepository pacienteRepository){
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public List<Paciente> listarTodo(){
        return this.pacienteRepository.findAll();
    }

    @Override
    public Paciente buscarPacientePorDocumento(String numDocumento) {

        return pacienteRepository.findByNumDoc(numDocumento)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
    }

    @Override
    public List<Paciente> PacientesPorFechaDeNacimiento(){
        return this.pacienteRepository.findAllByOrderByFecNacAsc();
    }
}
