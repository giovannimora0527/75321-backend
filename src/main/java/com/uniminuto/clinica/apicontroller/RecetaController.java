package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.service.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/recetas")
@CrossOrigin(origins = "*")
public class RecetaController {

    @Autowired
    private RecetaService recetaService;

    @PostMapping
    public ResponseEntity<Receta> crearReceta(@RequestBody Receta receta) {
        Receta recetaGuardada = recetaService.guardarReceta(receta);
        return new ResponseEntity<>(recetaGuardada, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Receta> listarRecetas() {
        return recetaService.listarRecetasOrdenadas();
    }

    @GetMapping("/cita/{citaId}")
    public List<Receta> listarRecetasPorCita(@PathVariable Long citaId) {
        return recetaService.listarRecetasPorCita(citaId);
    }
}