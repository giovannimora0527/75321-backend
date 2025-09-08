/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.service.PacienteService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge
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
    public Paciente obtenerPacientesPorDocumento(String numeroDocumento)
        throws BadRequestException {
        Optional<Paciente> optPaciente;
        optPaciente = this.pacienteRepository
                .findByNumeroDocumento(numeroDocumento);
        if (!optPaciente.isPresent()) {
            throw new BadRequestException("No existe el usuario");
        }        
       return optPaciente.get();
    }

    
}
