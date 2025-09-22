package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.repository.RecetaRepository;
import com.uniminuto.clinica.service.RecetaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementación de RecetaService que maneja la lógica de negocio.
 */
@Service
public class RecetaServiceImpl implements RecetaService {

    @Autowired
    private RecetaRepository recetaRepository;

    @Override
    public Receta crearReceta(Receta receta) {
        return recetaRepository.save(receta);
    }

    @Override
    public List<Receta> listarRecetas() {
        return recetaRepository.findAll();
    }
}
