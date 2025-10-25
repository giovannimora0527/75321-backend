package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.service.PacienteService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para gestión de pacientes.
 *
 * @author lmora
 */
@RestController
@RequestMapping("/pacientes")
public class PacienteApiController {

    /**
     * Servicio para operaciones de pacientes.
     */
    private final PacienteService pacienteService;

    /**
     * Constructor del controlador.
     *
     * @param pacienteService servicio de pacientes
     */
    public PacienteApiController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    /**
     * Lista pacientes ordenados por fecha de nacimiento.
     *
     * @return lista de pacientes ordenados
     */
    @GetMapping("/ordenados-por-fecha-nacimiento")
    public List<Paciente> listarPacientesOrdenadosPorFechaNacimiento() {
        return pacienteService.listarPacientesPorFechaNacimientoDesc();
    }
}