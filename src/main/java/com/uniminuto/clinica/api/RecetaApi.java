package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Receta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/receta")
public interface RecetaApi {

    @PostMapping("/crear")
    ResponseEntity<Receta> crearReceta(@RequestBody Receta receta);

    @GetMapping("/listar")
    ResponseEntity<List<Receta>> listarRecetas();
}
