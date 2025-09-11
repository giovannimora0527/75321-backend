package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Paciente;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.PacienteRq;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "*")
@RequestMapping("/paciente")

public interface PacienteAPi {
    
    @RequestMapping(value = "/all",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Paciente>> listarPacientes();
    
    @RequestMapping(value = "/find-doc",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Paciente> encontrarPacientePorDocumento(
            @RequestParam String numero_documento
    ) throws BadRequestException;
}
