package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Paciente;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author lmora
 */
@CrossOrigin(origins = "*")
@RequestMapping("/paciente")
public interface PacienteApi {
    
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            
            method = RequestMethod.GET)
    ResponseEntity<List<Paciente>> listarPacientes();
    
    @RequestMapping(value = "/buscar-por-documento",
            produces = {"application/json"},
            
            method = RequestMethod.GET)
    ResponseEntity<Paciente> buscarPorDocumento(
            @RequestParam String numeroDocumento
    ) throws BadRequestException;
}