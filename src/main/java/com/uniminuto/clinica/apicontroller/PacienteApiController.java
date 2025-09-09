package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.PacienteApi;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.awt.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PacienteApiController implements PacienteApi {
    private final PacienteService pacienteService;

    @GetMapping(value = "/listar", produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public List<Paciente> obtenerTodos() {
        return pacienteService.obtenerTodos();
    }

    @Override
    public List<Paciente> listarPorFechaNacimientoAsc() {
        return pacienteService.listarPorFechaNacimientoAsc();
    }


}
