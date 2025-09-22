package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paciente")
public class PacienteApiController {

    @Autowired
    private PacienteService pacienteService;

    // LISTAR TODOS LOS PACIENTES
    @GetMapping("/listar")
    public ResponseEntity<List<Paciente>> listarPacientes() {
        return ResponseEntity.ok(pacienteService.listarTodo());
    }

    // CREAR PACIENTE
    @PostMapping("/crear")
    public ResponseEntity<Paciente> crearPaciente(@RequestBody Paciente paciente) {
        return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
    }

    // BUSCAR PACIENTE POR DOCUMENTO
    @GetMapping("/documento/{numero}")
    public ResponseEntity<Paciente> buscarPorDocumento(@PathVariable String numero) {
        Paciente paciente = pacienteService.buscarPorDocumento(numero);
        if (paciente != null) {
            return ResponseEntity.ok(paciente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // LISTAR PACIENTES ORDENADOS POR EDAD
    @GetMapping("/listar-por-edad")
    public ResponseEntity<List<Paciente>> listarPacientesPorEdad() {
        return ResponseEntity.ok(pacienteService.listarPacientesPorEdad());
    }
}
