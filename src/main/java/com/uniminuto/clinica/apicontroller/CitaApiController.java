package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.service.CitaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaApiController {

    private final CitaService citaService;

    public CitaApiController(CitaService citaService){
        this.citaService = citaService;
    }

    @PostMapping
    public ResponseEntity<Cita> creaCita(@RequestBody Cita cita){
        return ResponseEntity.ok(citaService.guardarCita(cita));
    }

    @GetMapping
    public ResponseEntity<List<Cita>> listarCitas(){
        return ResponseEntity.ok(citaService.listarCitas());
    }
}
