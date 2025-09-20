package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.CitaApi;
import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.service.CitaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map; 

import java.util.List;

@RestController
public class CitaApiController implements CitaApi {

    @Autowired
    private CitaService citaService;

    @Override
    public ResponseEntity<Cita> crearCita(Map<String, Object> citaData) 
            throws BadRequestException {
        Long pacienteId = Long.valueOf(citaData.get("pacienteId").toString());
        Long medicoId = Long.valueOf(citaData.get("medicoId").toString());
        LocalDateTime fechaHora = LocalDateTime.parse(citaData.get("fechaHora").toString());
        String motivo = citaData.get("motivo") != null ? citaData.get("motivo").toString() : null;
        
        Cita cita = citaService.crearCita(pacienteId, medicoId, fechaHora, motivo);
        return ResponseEntity.ok(cita);
    }

    @Override
    public ResponseEntity<Cita> actualizarCita(Long citaId, LocalDateTime fechaHora, String motivo) 
            throws BadRequestException {
        Cita cita = citaService.actualizarCita(citaId, fechaHora, motivo);
        return ResponseEntity.ok(cita);
    }

    @Override
    public ResponseEntity<Cita> cambiarEstado(Long citaId, Cita.EstadoCita estado) 
            throws BadRequestException {
        Cita cita = citaService.cambiarEstado(citaId, estado);
        return ResponseEntity.ok(cita);
    }

    @Override
    public ResponseEntity<List<Cita>> listarCitas() {
        return ResponseEntity.ok(citaService.listarCitas());
    }

    @Override
    public ResponseEntity<List<Cita>> listarCitasPorPaciente(Long pacienteId) {
        return ResponseEntity.ok(citaService.listarCitasPorPaciente(pacienteId));
    }

    @Override
    public ResponseEntity<List<Cita>> listarCitasPorMedico(Long medicoId) {
        return ResponseEntity.ok(citaService.listarCitasPorMedico(medicoId));
    }

    @Override
    public ResponseEntity<Cita> obtenerCita(Long citaId) throws BadRequestException {
        return ResponseEntity.ok(citaService.buscarCitaPorId(citaId));
    }

    @Override
public ResponseEntity<List<Cita>> listarCitasPorFechaReciente() {
    return ResponseEntity.ok(citaService.listarCitasPorFechaReciente());
}
}