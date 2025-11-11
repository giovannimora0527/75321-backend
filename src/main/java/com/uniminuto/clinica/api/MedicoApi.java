package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Medico;

import java.util.List;

import com.uniminuto.clinica.model.MedicoRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.UsuarioRq;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 *
 * @author lmora
 */
@CrossOrigin(origins = "*")
@RequestMapping("/medico")
public interface MedicoApi {

    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Medico>> listarMedicos();


    @RequestMapping(value = "/listar-x-cod-esp",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Medico>> listarMedicosporEspecialidad(
            @RequestParam String codigo
    ) throws BadRequestException;

    @RequestMapping(value = "/guardar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> guardarMedico(
            @RequestBody @Valid MedicoRq medicoRq
    ) throws BadRequestException;



}
