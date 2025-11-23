package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.model.CitaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/cita")
public interface CitaApi {

    /**
     * Api para listar todas las citas del sistema.
     * @return listado de citas.
     */
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Cita>> listarCitas();

    /**
     * Api para guardar una cita nueva.
     * @param citaRq cita de entrada.
     * @return Respuesta del servicio.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/guardar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> guardarCita(
            @RequestBody @Valid CitaRq citaRq
            ) throws BadRequestException;

    /**
     * Api para listar todas las citas del sistema.
     * @return listado de citas.
     */
    @RequestMapping(value = "/listar-citas-paciente",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Cita>> listarCitasPorPaciente(
            @RequestParam Integer pacienteIds
    ) throws BadRequestException;
}
