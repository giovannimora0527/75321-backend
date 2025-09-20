/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.repository.MedicoRepository;
import com.uniminuto.clinica.service.MedicoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author johnq
 */
@Service
public class MedicoServiceImpl implements MedicoService {
    
    @Autowired
    private MedicoRepository medicoRepository;
    
    @Override
    public List<Medico> listarTodosLosMedicos() {
        return this.medicoRepository.findAll();
    }
    
}
