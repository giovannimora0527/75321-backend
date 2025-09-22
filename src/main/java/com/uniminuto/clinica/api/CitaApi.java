package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Cita;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * API para exponer endpoints de Cita.
 */
@CrossOrigin(origins = "*")
@RequestMapping("/cita")
public interface CitaApi {

    @PostMapping("/crear")
    ResponseEntity<Cita> crearCita(@RequestBody Cita cita);

    @GetMapping("/listar")
    ResponseEntity<List<Cita>> listarCitas();
}
