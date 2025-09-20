package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteApiController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/all")
    public ResponseEntity<List<Paciente>> listarPacientes() {
        return ResponseEntity.ok(pacienteService.listarTodos());
    }

    @GetMapping("/find-documento")
    public ResponseEntity<Paciente> buscarPorNumeroDocumento(@RequestParam String numeroDocumento) {
        Paciente paciente = pacienteService.buscarPorNumeroDocumento(numeroDocumento);
        if (paciente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(paciente);
    }
     @GetMapping("/por-fecha-nacimiento")
    public ResponseEntity<List<Paciente>> listarPorFechaNacimiento() {
        return ResponseEntity.ok(pacienteService.listarPorFechaNacimiento());
    }
}