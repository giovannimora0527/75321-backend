package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Receta;
import java.util.List;

/**
 * Servicio para la lógica de negocio de recetas médicas.
 *
 * @author lmora
 */
public interface RecetaService {

    /**
     * Guarda una nueva receta en el sistema.
     *
     * @param receta datos de la receta a guardar
     * @return receta guardada
     */
    Receta guardarReceta(Receta receta);

    /**
     * Lista todas las recetas ordenadas.
     *
     * @return lista de recetas ordenadas
     */
    List<Receta> listarRecetasOrdenadas();

    /**
     * Lista recetas por ID de cita médica.
     *
     * @param citaId ID de la cita
     * @return lista de recetas de la cita
     */
    List<Receta> listarRecetasPorCita(Long citaId);

    Receta actualizarReceta(Long id, Receta receta);


}