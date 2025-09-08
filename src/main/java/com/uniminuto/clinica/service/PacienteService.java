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
 * @author jorge
 */
public interface PacienteService {

    List<Paciente> listarTodo();
    
    Paciente obtenerPacientesPorDocumento(String numeroDocumento)
            throws BadRequestException;

}