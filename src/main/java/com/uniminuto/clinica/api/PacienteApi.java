package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Paciente;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;



@RequestMapping("/pacientes")//base del path
public interface PacienteApi {
    @GetMapping //Get pacientes
    List<Paciente>obtenerTodos();

    //https://localhost:8000/clinica/pacientes/listar

    //Declaramos el sub path y pasamos impl
    // https://localhost:8000/clinica/pacientes/listar-por-fecha-nacimiento
    @GetMapping("/listar-por-fecha-nacimiento")
    List<Paciente>listarPorFechaNacimientoAsc();
}
