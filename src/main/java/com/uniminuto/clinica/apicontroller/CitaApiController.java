package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST para gestión de citas médicas.
 *
 * @author lmora
 */
@RestController
@RequestMapping("/citas")
public class CitaApiController {

    /**
     * Servicio para operaciones de citas.
     */
    @Autowired
    private CitaService citaService;

    /**
     * Crea una nueva cita médica.
     *
     * @param cita datos de la cita a crear
     * @return cita creada
     */
    @PostMapping
    public Cita crearCita(@RequestBody Cita cita) {
        return citaService.guardarCita(cita);
    }

    /**
     * Lista todas las citas ordenadas.
     *
     * @return lista de citas ordenadas
     */
    @GetMapping("/ordenadas")
    public List<Cita> listarCitasOrdenadas() {
        return citaService.listarCitasOrdenadas();
    }
}