package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Cita;
import org.apache.coyote.BadRequestException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RequestMapping("/citas")
public interface CitaApi {

    @PostMapping
    ResponseEntity<Cita> crearCita(@RequestBody Map<String, Object> citaData) throws BadRequestException;


    @PutMapping("/{citaId}")
    ResponseEntity<Cita> actualizarCita(
            @PathVariable Long citaId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaHora,
            @RequestParam(required = false) String motivo
    ) throws BadRequestException;

    @PatchMapping("/{citaId}/estado")
    ResponseEntity<Cita> cambiarEstado(
            @PathVariable Long citaId,
            @RequestParam Cita.EstadoCita estado
    ) throws BadRequestException;

    @GetMapping
    ResponseEntity<List<Cita>> listarCitas();

    @GetMapping("/paciente/{pacienteId}")
    ResponseEntity<List<Cita>> listarCitasPorPaciente(
            @PathVariable Long pacienteId
    );

    @GetMapping("/medico/{medicoId}")
    ResponseEntity<List<Cita>> listarCitasPorMedico(
            @PathVariable Long medicoId
    );

    @GetMapping("/{citaId}")
    ResponseEntity<Cita> obtenerCita(
            @PathVariable Long citaId
    ) throws BadRequestException;

       @GetMapping("/recientes")
    ResponseEntity<List<Cita>> listarCitasPorFechaReciente();
    
}