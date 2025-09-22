package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Paciente;

import java.util.List;

public interface PacienteService {

    List<Paciente> listarTodo();

    Paciente guardarPaciente(Paciente paciente);

    Paciente buscarPorDocumento(String numeroDocumento);

    List<Paciente> listarPacientesPorEdad();
}
