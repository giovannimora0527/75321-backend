package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.model.CitaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 * @author AORUS
 */
public interface CitaService {
    
    List<Cita> listarTodasLasCitas();
    
    List<Cita> listarCitasPorPaciente(Integer pacienteId);
    
    List<Cita> listarCitasPorMedico(Integer medicoId);
    
    List<Cita> listarCitasPorEstado(String estado);
    
    RespuestaRs guardarCita(CitaRq citaNueva) throws BadRequestException;

    List<Cita> listarCitasPasadasOrdenadas();
}
