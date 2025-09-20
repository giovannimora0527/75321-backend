package com.uniminuto.clinica.apicontroller;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.service.PacienteService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pacientes")
public class PacienteApiController {
    private final PacienteService pacienteService;

    public PacienteApiController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping("/ordenados-por-fecha-nacimiento")
    public List<Paciente> listarPacientesOrdenadosPorFechaNacimiento() {
        return pacienteService.listarPacientesPorFechaNacimientoDesc();
    }
}