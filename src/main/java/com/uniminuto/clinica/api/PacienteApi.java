package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.model.PacienteRq;
import com.uniminuto.clinica.model.PacienteRs;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;

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

    //firma api Guardar Pacientes
    @PostMapping(value = "/guardar",consumes ="application/json",produces = "application/json")
    PacienteRs guardarPaciente(@RequestBody PacienteRq pacienteNuevo)throws BadRequestException;

    //Firma api actualizar Pacientes
    @PutMapping(value = "/actualizar",consumes = "application/json",produces = "application/json")
    PacienteRs actualizarPaciente(@RequestBody PacienteRq pacienteEditado) throws BadRequestException;
}
