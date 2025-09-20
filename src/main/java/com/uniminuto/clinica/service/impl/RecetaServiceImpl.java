package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.repository.RecetaRepository;
import com.uniminuto.clinica.repository.CitaRepository;
import com.uniminuto.clinica.service.RecetaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecetaServiceImpl implements RecetaService{

    private final RecetaRepository recetaRepository;
    private final CitaRepository citaRepository;

    public RecetaServiceImpl(RecetaRepository recetaRepository, CitaRepository citaRepository) {
        this.recetaRepository = recetaRepository;
        this.citaRepository = citaRepository;
    }

    @Override
    public Receta crearReceta(Receta receta) {

        Cita cita = citaRepository.findById(receta.getCita().getId())
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        receta.setCita(cita);
        return recetaRepository.save(receta);
    }

    @Override
    public List<Receta> listarRecetas() {
        return recetaRepository.findAllByOrderByFechaCreacionDesc();
    }
}
