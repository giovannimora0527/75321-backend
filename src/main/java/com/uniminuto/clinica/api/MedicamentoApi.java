package com.uniminuto.clinica.api;

import com.uniminuto.clinica.model.CrearMedicamentoRq;
import com.uniminuto.clinica.model.MedicamentoRs;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(path = "/medicamentos", produces = MediaType.APPLICATION_JSON_VALUE)
public interface MedicamentoApi {

    //Api pare Crear medicamentos
    @PostMapping(path = "/crear", consumes = MediaType.APPLICATION_JSON_VALUE)
    MedicamentoRs crear(@Valid @RequestBody CrearMedicamentoRq rq);

    //Listar Medicamentos
    @GetMapping("/listar")
    List<MedicamentoRs>listar();

    @PostMapping(path = "/actualizar/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    MedicamentoRs actualizar(@PathVariable Long id, @Valid @RequestBody CrearMedicamentoRq rq);


}
