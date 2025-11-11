package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Paciente;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author lmora
 */
public interface PacienteService {
    /**
     * Lista todos los pacientes de la bd.
     * @return Lista de pacientes.
     */
    List<Paciente> encontrarTodosLosPacientes();

    /**
     * Busca un paciente dado un documento de identidad.
     * @param documento documento a buscar.
     * @return Paciente encontrado.
     * @throws BadRequestException excepcion.
     */
    Paciente buscarPacientePorDocumento(String documento) throws BadRequestException;


    List<Paciente> listarOrdenadoPorFechaNacimiento(boolean ascendente);
}