package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.PacienteRq;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 * @author AORUS
 */

public interface PacienteService {
    List<Paciente> listarTodo();
    
    Paciente buscarPacientePorDocumento(String documento) 
        throws BadRequestException;
    
    List<Paciente> listarPacientesOrdenadosPorFechaNacimiento();
    
    RespuestaRs guardarPaciente(PacienteRq pacienteNuevo) 
        throws BadRequestException;
    
    RespuestaRs actualizarPaciente(PacienteRq pacienteActualizar) 
        throws BadRequestException;
}
