package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.model.CitaRq;
import com.uniminuto.clinica.model.RespuestaRs;
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
@RequestMapping("/cita")
public interface CitaApi {
    
    @RequestMapping(value = "/all",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Cita>> listarCitas();

    @RequestMapping(value = "/ordenadas_porHora",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Cita>> listarCitasPasadasOrdenadas();
    
    @RequestMapping(value = "/by-paciente",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Cita>> listarCitasPorPaciente(
            @RequestParam Integer pacienteId
    );
    
    @RequestMapping(value = "/by-medico",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Cita>> listarCitasPorMedico(
            @RequestParam Integer medicoId
    );
    
    @RequestMapping(value = "/by-estado",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Cita>> listarCitasPorEstado(
            @RequestParam String estado
    );
    
    @RequestMapping(value = "/guardar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> guardarCita(
            @RequestBody CitaRq cita
    ) throws BadRequestException;
}
