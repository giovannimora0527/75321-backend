package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.entity.Usuario;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author lmora
 */
@CrossOrigin(origins = "*")
@RequestMapping("/medico")
public interface MedicoApi {

    @GetMapping(value = "/listar", produces = "application/json")
    ResponseEntity<List<Medico>> listarMedicos();

    @GetMapping(value = "/buscar-por-especialidad", produces = "application/json")
    ResponseEntity<List<Medico>> listarPorEspecialidad(
            @RequestParam String codigoEspecializacion
    ) throws BadRequestException;

    @PostMapping(value = "/guardar" , consumes = "application/json", produces = "application/json")
    ResponseEntity<Medico>
        guardar(@RequestBody Medico medico) throws BadRequestException;
}
