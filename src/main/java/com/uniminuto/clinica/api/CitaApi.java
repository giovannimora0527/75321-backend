package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.model.CitaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.*;
import jakarta.validation.constraints.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RequestMapping("/cita")
public interface CitaApi {

    @GetMapping(value = "/listar", produces = "application/json")
    ResponseEntity<List<Cita>> listarCitas();


    @PostMapping(value = "/guardar",
            produces = "application/json",
            consumes = "application/json")
    ResponseEntity<RespuestaRs> guardarCitas(
            @RequestBody @Valid CitaRq citaRq
    ) throws BadRequestException;

}
