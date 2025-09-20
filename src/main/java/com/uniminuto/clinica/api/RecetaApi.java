package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Receta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/receta")
public interface RecetaApi {

    @PostMapping ("/crearReceta")
    ResponseEntity<Receta> crearReceta(@RequestBody Receta receta);

    @GetMapping ("/listarRecetas")
    ResponseEntity<List<Receta>> listarRecetas();

}
