package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Paciente;
import java.util.List;

public interface PacienteService {
    List<Paciente> listarTodos();
    Paciente buscarPorNumeroDocumento(String numeroDocumento);
    List<Paciente> listarPorFechaNacimiento();
}