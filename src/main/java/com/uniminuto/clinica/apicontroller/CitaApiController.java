package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.CitaApi;
import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.model.CitaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.CitaService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author AORUS
 */
@RestController
public class CitaApiController implements CitaApi {
    
    @Autowired
    private CitaService citaService;
    
    @Override
    public ResponseEntity<List<Cita>> listarCitas() {
        return ResponseEntity.ok(this.citaService.listarTodasLasCitas());
    }
    
     @Override
    public ResponseEntity<List<Cita>> listarCitasPorPaciente(Integer pacienteId) {
        return ResponseEntity.ok(this.citaService.listarCitasPorPaciente(pacienteId));
    }

    //crea el controlador para listar citas por horas
    @Override
    public ResponseEntity<List<Cita>> listarCitasPasadasOrdenadas() {
        return ResponseEntity.ok(this.citaService.listarCitasPasadasOrdenadas());
    }
    
    @Override
    public ResponseEntity<List<Cita>> listarCitasPorMedico(Integer medicoId) {
        return ResponseEntity.ok(this.citaService.listarCitasPorMedico(medicoId));
    }
    
    @Override
    public ResponseEntity<List<Cita>> listarCitasPorEstado(String estado) {
        return ResponseEntity.ok(this.citaService.listarCitasPorEstado(estado));
    }
    
    @Override
    public ResponseEntity<RespuestaRs> guardarCita(CitaRq cita) throws BadRequestException {
        return ResponseEntity.ok(this.citaService.guardarCita(cita));
    }
}
