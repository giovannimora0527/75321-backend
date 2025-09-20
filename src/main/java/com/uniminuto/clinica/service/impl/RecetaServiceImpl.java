/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.repository.RecetaRepository;
import com.uniminuto.clinica.service.RecetaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author johnq
 */
@Service
public class RecetaServiceImpl implements RecetaService{
    
    @Autowired
    private RecetaRepository repository;

    @Override
    public Receta guardar(Receta receta) {
        return this.repository.save(receta);
    }

    @Override
    public List<Receta> listarRecetaPorFecha() {
        return this.repository.findAllByOrderByFechaCreacionDesc();
    }
}
