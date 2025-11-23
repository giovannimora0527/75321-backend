package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Paciente;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = "*")
@RequestMapping("/paciente")
public interface PacienteApi {

    /**
     * Lista los usuarios de la bd.
     *
     * @return
     */
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Paciente>> listarPacientes();


    @RequestMapping(value = "/buscar-paciente-documento",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Paciente> buscarPacienteXIdentificacion(
            @RequestParam String numeroDocumento)
            throws BadRequestException;


    /**
     * Lista los usuarios de la bd.
     *
     * @return
     */
    @RequestMapping(value = "/listar-orden-fecha-nacimiento",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Paciente>> listarPacientesXOrden(
            @RequestParam String orden
    );
}
