package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/receta")
public interface RecetaApi {

    @GetMapping(value = "/listar", produces = "application/json")
    ResponseEntity<List<Receta>> listarRecetas();

    @PostMapping(value = "/guardar", produces = "application/json", consumes = "application/json")
    ResponseEntity<RespuestaRs> guardarRecetas(
            @RequestBody @Valid RecetaRq recetaRq
    ) throws BadRequestException;

}
