package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.model.CitaRs;

import java.util.List;

public interface CitaService {

    Cita guardarCita (CitaRs citaRs);

    List<Cita> listarCitas();

}
