package com.uniminuto.clinica.service;

public interface PacienteService {

    List<Paciente> listarTodo();
    
    Paciente buscarPacientePorDocumento(String documento) 
        throws BadRequestException;
}
