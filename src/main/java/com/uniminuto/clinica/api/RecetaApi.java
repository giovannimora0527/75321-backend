package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/receta")
public interface RecetaApi {

    /**
     * Api para listar todas las recetas del sistema.
     * @return listado de recetas.
     */
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Receta>> listarRecetas();


    /**
     * Api para guardar una receta en el sistema.
     * @return respuesta del servicio.
     */
    @RequestMapping(value = "/guardar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> guardarReceta(
           @RequestBody @Valid RecetaRq recetaRq
    ) throws BadRequestException;

    /**
     * Api para acualizar una receta en el sistema.
     * @return respuesta del servicio.
     */
    @RequestMapping(value = "/actualizar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> actualizarReceta(
            @RequestBody @Valid RecetaRq recetaRq
    ) throws BadRequestException;
}
