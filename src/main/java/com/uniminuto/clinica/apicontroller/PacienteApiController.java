package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.PacienteApi;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.service.PacienteService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PacienteApiController implements PacienteApi{

    private final PacienteService pacienteService;

   public PacienteApiController(PacienteService pacienteService){
       this.pacienteService = pacienteService;
   }

   @Override
    public ResponseEntity<List<Paciente>> listarPacientes() {
        return ResponseEntity.ok(this.pacienteService.listarTodo());
    }

    @Override
    public ResponseEntity<Paciente> encontrarPacientePorDocumento(String numero_documento)
            throws BadRequestException {
        return ResponseEntity.ok(this.pacienteService.buscarPacientePorDocumento(numero_documento));
    }

    @Override
    public ResponseEntity<List<Paciente>> listarPacientesPorFecha() {
        return ResponseEntity.ok(this.pacienteService.PacientesPorFechaDeNacimiento());
    }
}
