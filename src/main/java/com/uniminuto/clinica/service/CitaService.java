package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Cita;
import org.apache.coyote.BadRequestException;

import java.time.LocalDateTime;
import java.util.List;

public interface CitaService {
    Cita crearCita(Long pacienteId, Long medicoId, LocalDateTime fechaHora, String motivo) 
        throws BadRequestException;
    
    Cita actualizarCita(Long citaId, LocalDateTime nuevaFechaHora, String nuevoMotivo) 
        throws BadRequestException;
    
    Cita cambiarEstado(Long citaId, Cita.EstadoCita nuevoEstado) 
        throws BadRequestException;
    
    List<Cita> listarCitas();
    
    List<Cita> listarCitasPorPaciente(Long pacienteId);
    
    List<Cita> listarCitasPorMedico(Long medicoId);
    
    Cita buscarCitaPorId(Long citaId) throws BadRequestException;

        List<Cita> listarCitasPorFechaReciente();
}