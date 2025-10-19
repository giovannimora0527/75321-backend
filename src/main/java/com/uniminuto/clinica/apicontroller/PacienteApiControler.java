package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.PacienteApi;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.model.PacienteRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.PacienteService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lmora
 */
@RestController
public class PacienteApiControler implements PacienteApi {

    @Autowired
    private PacienteService pacienteService;

    @Override
    public ResponseEntity<List<Paciente>> listarPaciente() {
        return ResponseEntity.ok(this.pacienteService.listarTodo());
    }

    @Override
    public ResponseEntity<List<Paciente>> 
        listarPacientePorDocumento(String numeroDocumento) 
                throws BadRequestException{
       return ResponseEntity.ok(this.pacienteService
               .obtenerPacientePorDocumento(numeroDocumento));
    }

    @Override
    public ResponseEntity<List<Paciente>> listarPacientesMayorAMenor() {
        return ResponseEntity.ok(this.pacienteService.listarPacientesMayorAMenor());
    }

    @Override
    public ResponseEntity<RespuestaRs> guardarPaciente(PacienteRq pacienteRq)
            throws BadRequestException {
        return ResponseEntity.ok(this.pacienteService.guardarPaciente(pacienteRq));

    }

    @Override
    public ResponseEntity<RespuestaRs> actualizarPaciente(PacienteRq pacienteRq)
            throws BadRequestException {
        return ResponseEntity.ok(this.pacienteService.actualizarPaciente(pacienteRq));

    }
}
