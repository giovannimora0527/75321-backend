package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.PacienteApi;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.PacienteRq;
import com.uniminuto.clinica.service.PacienteService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author AORUS
 */

@RestController
public class PacienteApiController implements PacienteApi {
    
    @Autowired
    private PacienteService pacienteService;
    
    @Override
    public ResponseEntity<List<Paciente>> listarPacientes() {
        return ResponseEntity.ok(this.pacienteService.listarTodo());
    }
    
    @Override
    public ResponseEntity<Paciente> encontrarPacientePorDocumento(String numero_documento)
            throws BadRequestException {
        return ResponseEntity.ok(this.pacienteService
                .buscarPacientePorDocumento(numero_documento));
    }
    
    @Override
    public ResponseEntity<List<Paciente>> listarPacientesOrdenadosPorFechaNacimiento() {
        return ResponseEntity.ok(this.pacienteService
                .listarPacientesOrdenadosPorFechaNacimiento());
    }
    
    @Override
    public ResponseEntity<RespuestaRs> guardarPaciente(PacienteRq paciente)
            throws BadRequestException {
        return ResponseEntity.ok(this.pacienteService.guardarPaciente(paciente));
    }
    
    @Override
    public ResponseEntity<RespuestaRs> actualizarPaciente(PacienteRq paciente)
            throws BadRequestException {
        return ResponseEntity.ok(this.pacienteService.actualizarPaciente(paciente));
    }
}
