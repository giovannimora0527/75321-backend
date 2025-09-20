package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.model.CitaRs;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/cita")
public interface CitaApi {

    @PostMapping("/agendarCita")
    ResponseEntity<Cita> crearCita(@RequestBody CitaRs citaRs);

    @GetMapping(
    value = "/listarCitas",
    produces = "application/json"
    )
    ResponseEntity<List<Cita>> listarCitas();


}
