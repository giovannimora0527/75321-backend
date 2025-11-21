package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Especializacion;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author lmora
 */
@CrossOrigin(origins = "*")
@RequestMapping("/especializacion")
public interface EspecializacionApi {
    
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Especializacion>> listarEspecializaciones();
    
    @RequestMapping(value = "/buscar-por-codigo",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Especializacion> buscarPorCodigo(
            @RequestParam String codigo
    ) throws BadRequestException;
    
    @RequestMapping(value = "/crear",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<Especializacion> crearEspecializacion(
            @RequestBody Especializacion especializacion
    ) throws BadRequestException;
    
    @RequestMapping(value = "/actualizar/{id}",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<Especializacion> actualizarEspecializacion(
            @PathVariable Long id,
            @RequestBody Especializacion especializacion
    ) throws BadRequestException;
}
