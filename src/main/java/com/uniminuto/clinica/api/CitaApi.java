package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Cita;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/cita")
public interface CitaApi {

    @PostMapping
    ResponseEntity<Cita> crearCita(@RequestBody Cita cita);

    @GetMapping
    ResponseEntity<List<Cita>> listarCitas();
}
