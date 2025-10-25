package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.service.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST para gestión de recetas médicas.
 *
 * @author lmora
 */
@RestController
@RequestMapping("/api/recetas")
@CrossOrigin(origins = "*")
public class RecetaController {

    /**
     * Servicio para operaciones de recetas.
     */
    @Autowired
    private RecetaService recetaService;

    /**
     * Crea una nueva receta médica.
     *
     * @param receta datos de la receta a crear
     * @return receta creada
     */
    @PostMapping
    public ResponseEntity<Receta> crearReceta(@RequestBody Receta receta) {
        Receta recetaGuardada = recetaService.guardarReceta(receta);
        return new ResponseEntity<>(recetaGuardada, HttpStatus.CREATED);
    }

    /**
     * Lista todas las recetas ordenadas.
     *
     * @return lista de recetas
     */
    @GetMapping
    public List<Receta> listarRecetas() {
        return recetaService.listarRecetasOrdenadas();
    }

    /**
     * Lista recetas por cita médica.
     *
     * @param citaId ID de la cita
     * @return lista de recetas de la cita
     */
    @GetMapping("/cita/{citaId}")
    public List<Receta> listarRecetasPorCita(@PathVariable Long citaId) {
        return recetaService.listarRecetasPorCita(citaId);
    }

    // 🔹 Nuevo endpoint para actualizar receta
    @PutMapping("/{id}")
    public ResponseEntity<Receta> actualizarReceta(@PathVariable Long id, @RequestBody Receta receta) {
        try {
            Receta actualizada = recetaService.actualizarReceta(id, receta);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}