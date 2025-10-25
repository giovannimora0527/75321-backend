package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Cita;
import java.util.List;

/**
 * Servicio para la lógica de negocio de citas médicas.
 *
 * @author lmora
 */
public interface CitaService {

    /**
     * Guarda una nueva cita en el sistema.
     *
     * @param cita datos de la cita a guardar
     * @return cita guardada
     */
    Cita guardarCita(Cita cita);

    /**
     * Lista todas las citas ordenadas.
     *
     * @return lista de citas ordenadas
     */
    List<Cita> listarCitasOrdenadas();
}