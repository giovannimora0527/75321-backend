package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface RecetaService {
    /**
     * Lista las recetas medicas del sistema.
     * @return Listado de recetas medicas.
     */
    List<Receta> listarRecetas();

    /**
     * Guarda una nueva receta médica en el sistema.
     * @param recetaRq receta a guardar.
     * @return Respuesta del proceso.
     * @throws BadRequestException excepcion del sistema.
     */
    RespuestaRs guardarReceta(RecetaRq recetaRq) throws BadRequestException;

    /**
     * Actualiza una receta médica existente en el sistema.
     * @param recetaRq receta a actualizar.
     * @return Respuesta del proceso.
     * @throws BadRequestException excepcion del sistema.
     */
    RespuestaRs actualizarReceta(RecetaRq recetaRq) throws BadRequestException;
}
