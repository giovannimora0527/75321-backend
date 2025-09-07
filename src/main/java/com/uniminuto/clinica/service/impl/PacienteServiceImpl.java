package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //  Para indetificar que es un servicio
@RequiredArgsConstructor// para no hacer otra vez el constructor
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;

    @Override //Implementamos el metodo
    public List<Paciente> obtenerTodos() {

        return pacienteRepository.findAll();
    }
}
