package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Medico;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * API para gestión de médicos.
 *
 * @author lmora
 */
@RequestMapping("/medico")
public interface MedicoApi {

    /**
     * Lista todos los médicos.
     *
     * @return lista de médicos
     */
    @RequestMapping(
            value = "/listar",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    ResponseEntity<List<Medico>> listarMedicos();

    /**
     * Busca médicos por especialidad.
     *
     * @param codigoEspecializacion código de especialidad
     * @return lista de médicos
     * @throws BadRequestException si código inválido
     */
    @RequestMapping(
            value = "/buscar-por-especialidad",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    ResponseEntity<List<Medico>> listarPorEspecialidad(
            @RequestParam String codigoEspecializacion
    ) throws BadRequestException;
}