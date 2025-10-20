package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.PacienteRq;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author AORUS
 */

@CrossOrigin(origins = "*")
@RequestMapping("/paciente")
public interface PacienteApi {
    
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
    
    @RequestMapping(value = "/all-by-FecNac",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Paciente>> listarPacientesOrdenadosPorFechaNacimiento();
    
    @RequestMapping(value = "/guardar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> guardarPaciente(
            @RequestBody PacienteRq paciente
    ) throws BadRequestException;
    
    @RequestMapping(value = "/actualizar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> actualizarPaciente(
            @RequestBody PacienteRq paciente
    ) throws BadRequestException;
}
