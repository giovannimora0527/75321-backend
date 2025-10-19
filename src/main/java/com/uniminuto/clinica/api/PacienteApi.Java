package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.model.PacienteRq;
import com.uniminuto.clinica.model.RecetaRq;
import java.util.List;

import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author lmora
 */
@CrossOrigin(origins = "*")
@RequestMapping("/paciente")
public interface PacienteApi {

    @GetMapping(value = "/listar",
                produces = "application/json")
    ResponseEntity<List<Paciente>> listarPaciente();

    @GetMapping(value = "/buscar-por-documento",
                produces = "application/json")
    ResponseEntity<List<Paciente>> listarPacientePorDocumento(
            @RequestParam String numeroDocumento
    ) throws BadRequestException;

    @GetMapping(value = "/listar-ordenado-nacimiento",
                produces = "application/json")
    ResponseEntity<List<Paciente>> listarPacientesMayorAMenor();

    @PostMapping(value = "/guardar",
            produces = "application/json",
            consumes = "application/json")
    ResponseEntity<RespuestaRs> guardarPaciente(PacienteRq pacienteRq)
            throws BadRequestException;

    @PutMapping(value = "/actualizar",
            produces = "application/json",
            consumes = "application/json")
    ResponseEntity<RespuestaRs> actualizarPaciente(PacienteRq pacienteRq)
            throws BadRequestException;
}
