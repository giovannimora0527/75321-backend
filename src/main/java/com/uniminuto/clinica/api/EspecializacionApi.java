package com.uniminuto.clinica.api;

import com.uniminuto.clinica.model.CrearEspecializacionRq;
import com.uniminuto.clinica.model.EspecializacionRs;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/especializaciones")
public interface EspecializacionApi {
    @PostMapping("/crear")
    ResponseEntity<EspecializacionRs> crear(@Valid @RequestBody CrearEspecializacionRq rq);

    @GetMapping("/listar")
    ResponseEntity<List<EspecializacionRs>> listar();
}
