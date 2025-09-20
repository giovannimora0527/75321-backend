package com.uniminuto.clinica.apicontroller;


import com.uniminuto.clinica.api.RecetaApi;
import com.uniminuto.clinica.entity.Receta;
import org.springframework.http.ResponseEntity;
import com.uniminuto.clinica.service.RecetaService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecetaApiController implements RecetaApi {

    private final RecetaService recetaService;

    public RecetaApiController(RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    @Override
    public ResponseEntity<Receta> crearReceta(Receta receta) {
        Receta recetaCreada = recetaService.crearReceta(receta);
        return ResponseEntity.ok(recetaCreada);
    }

    @Override
    public ResponseEntity<List<Receta>> listarRecetas() {
        List<Receta> recetas = recetaService.listarRecetas();
        return ResponseEntity.ok(recetas);
    }
}
