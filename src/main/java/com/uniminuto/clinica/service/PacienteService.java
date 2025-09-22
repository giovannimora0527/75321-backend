/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Paciente;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author AORUS
 */
public interface PacienteService {
    List<Paciente> listarTodo();
    
    Paciente buscarPacientePorDocumento(String documento) 
            throws BadRequestException;
    
    // Nuevo método para listar ordenado por fecha de nacimiento
    List<Paciente> listarPacientesOrdenadosPorFechaNacimiento();
}

