package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Paciente;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * API para gestión de pacientes.
 *
 * @author lmora
 */
@CrossOrigin(origins = "*")
@RequestMapping("/paciente")
public interface PacienteApi {

    /**
     * Lista todos los pacientes.
     *
     * @return lista de pacientes
     */
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Paciente>> listarPacientes();

    /**
     * Busca paciente por documento.
     *
     * @param numeroDocumento número de documento
     * @return paciente encontrado
     * @throws BadRequestException si documento inválido
     */
    @RequestMapping(value = "/buscar-por-documento",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Paciente> buscarPorDocumento(
            @RequestParam String numeroDocumento
    ) throws BadRequestException;
}