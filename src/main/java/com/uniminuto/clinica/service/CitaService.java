package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Cita;
import java.util.List;

/**
 *
 * @author lmora
 */
public interface CitaService {

    Cita guardar(Cita cita);

    List<Cita> listarCitasPorFecha();
}
