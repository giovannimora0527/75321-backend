package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.repository.CitaRepository;
import com.uniminuto.clinica.repository.RecetaRepository;
import com.uniminuto.clinica.service.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class RecetaServiceImpl implements RecetaService {
    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private CitaRepository citaRepository;

    @Override
    public Receta guardarReceta(Receta receta) {
        // Validar que la cita existe
        Cita cita = citaRepository.findById(receta.getCita().getId().longValue())
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        receta.setCita(cita);
        return recetaRepository.save(receta);
    }

    @Override
    public List<Receta> listarRecetasOrdenadas() {
        return recetaRepository.findAllByOrderByFechaCreacionRegistroDesc();
    }

    @Override
    public List<Receta> listarRecetasPorCita(Long citaId) {
        return recetaRepository.findByCitaId(citaId);
    }
}
