package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Medicamento;
import com.uniminuto.clinica.model.MedicamentoRq;
import com.uniminuto.clinica.model.RespuestaRs;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/medicamento")
public interface MedicamentoApi {

    @GetMapping(value = "/listar", produces = "application/json")
    ResponseEntity<List<Medicamento>> listarMedicamentos();

    @PostMapping(value = "/guardar",
            produces = "application/json",
            consumes = "application/json")
    ResponseEntity<RespuestaRs> guardarMedicamento(
            @RequestBody @Valid MedicamentoRq medicamentoRq
    ) throws BadRequestException;

    @PostMapping(value = "/actualizar",
            produces = "application/json",
            consumes = "application/json")
    ResponseEntity<RespuestaRs> actualizarMedicamento(
            @RequestBody @Valid MedicamentoRq medicamentoRq
    ) throws BadRequestException;

}
