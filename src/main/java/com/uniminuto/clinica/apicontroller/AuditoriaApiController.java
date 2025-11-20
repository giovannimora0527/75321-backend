package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.entity.LogAuditoria;
import com.uniminuto.clinica.repository.LogAuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/auditoria")
public class AuditoriaApiController {

    @Autowired
    private LogAuditoriaRepository logRepository;

    @GetMapping
    public ResponseEntity<Page<LogAuditoria>> obtenerLogs(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        // Convertir LocalDate a LocalDateTime para la consulta
        LocalDateTime inicio = (fechaInicio != null) ? fechaInicio.atStartOfDay() : null;
        LocalDateTime fin = (fechaFin != null) ? fechaFin.atTime(23, 59, 59) : null;

        Page<LogAuditoria> logs = logRepository.buscarLogs(
                username,
                tipo,
                inicio,
                fin,
                PageRequest.of(page, size, Sort.by("fecha").descending())
        );

        return ResponseEntity.ok(logs);
    }
}