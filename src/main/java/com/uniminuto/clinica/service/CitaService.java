package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Cita;
import java.util.List;

/**
 * Interface para los servicios de Cita.
 */
public interface CitaService {

    Cita guardarCita(Cita cita);

    List<Cita> listarCitasPorFecha();
}
