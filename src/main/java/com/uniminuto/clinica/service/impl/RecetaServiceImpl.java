package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.repository.CitaRepository;
import com.uniminuto.clinica.repository.RecetaRepository;
import com.uniminuto.clinica.service.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Implementación del servicio para la lógica de negocio de recetas médicas.
 * Maneja la creación, consulta y listado de recetas asociadas a citas.
 *
 * @author lmora
 */
@Service
public class RecetaServiceImpl implements RecetaService {
    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private CitaRepository citaRepository;

    /**
     * Guarda una nueva receta en el sistema validando que la cita asociada existe.
     * Establece la relación con la cita antes de persistir la receta.
     *
     * @param receta datos de la receta a guardar, debe contener una cita válida
     * @return receta guardada con la relación de cita establecida
     * @throws RuntimeException si la cita asociada no es encontrada
     */
    @Override
    public Receta guardarReceta(Receta receta) {
        // Validar que la cita existe
        Cita cita = citaRepository.findById(receta.getCita().getId().longValue())
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        receta.setCita(cita);
        return recetaRepository.save(receta);
    }

    /**
     * Lista todas las recetas ordenadas por fecha de creación de manera descendente.
     * Las recetas más recientes aparecen primero en la lista.
     *
     * @return lista de recetas ordenadas por fecha de creación descendente, vacía si no hay recetas
     */
    @Override
    public List<Receta> listarRecetasOrdenadas() {
        return recetaRepository.findAllByOrderByFechaCreacionRegistroDesc();
    }

    /**
     * Lista todas las recetas asociadas a una cita específica.
     * Útil para obtener el historial de medicamentos de una consulta.
     *
     * @param citaId identificador de la cita para filtrar recetas
     * @return lista de recetas asociadas a la cita especificada, vacía si no hay recetas
     */
    @Override
    public List<Receta> listarRecetasPorCita(Long citaId) {
        return recetaRepository.findByCitaId(citaId);
    }

    @Override
    public Receta actualizarReceta(Long id, Receta recetaActualizada) {
        // Buscar la receta existente
        Receta existente = recetaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada con ID: " + id));

        // Actualizar solo los campos editables
        existente.setMedicamentoId(recetaActualizada.getMedicamentoId());
        existente.setDosis(recetaActualizada.getDosis());
        existente.setIndicaciones(recetaActualizada.getIndicaciones());

        // Si viene una cita diferente o nueva, la validamos
        if (recetaActualizada.getCita() != null && recetaActualizada.getCita().getId() != null) {
            Cita cita = citaRepository.findById(recetaActualizada.getCita().getId().longValue())
                    .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
            existente.setCita(cita);
        }

        return recetaRepository.save(existente);
    }
}
