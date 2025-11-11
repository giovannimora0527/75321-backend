package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Medicamento;
import com.uniminuto.clinica.model.MedicamentoRq;
import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/medicamento")
public interface MedicamentoApi {

    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Medicamento>> listarMedicamentos();


    @RequestMapping(value = "/guardar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> guardarMedicamento(
            @RequestBody MedicamentoRq medicamentoRq
    ) throws BadRequestException;


    @RequestMapping(value = "/actualizar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> actualizarMedicamento(
            @RequestBody MedicamentoRq medicamentoRq
    ) throws BadRequestException;
}
