package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.CitaApi;
import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.model.CitaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.CitaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CitaApiController implements CitaApi {

    /**
     * Servicio de citas.
     */
    @Autowired
    private CitaService citaService;

    @Override
    public ResponseEntity<List<Cita>> listarCitas() {
        return ResponseEntity.ok(this.citaService.listarCitas());
    }

    @Override
    public ResponseEntity<RespuestaRs> guardarCita(@RequestBody @Valid CitaRq citaRq) throws BadRequestException {
        return ResponseEntity.ok(this.citaService.guardarCita(citaRq));
    }

    @Override
    public ResponseEntity<List<Cita>> listarCitasPorPaciente(Integer pacienteIds) throws BadRequestException {
        return ResponseEntity.ok(this.citaService.listarCitasporPaciente(pacienteIds));
    }
}
