package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author AORUS
 */
@CrossOrigin(origins = "*")
@RequestMapping("/receta")
public interface RecetaApi {

    @RequestMapping(value = "/all",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Receta>> listarTodasLasRecetas();

    @RequestMapping(value = "/by-cita",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Receta>> listarRecetasPorCita(
            @RequestParam Integer citaId
    );

    @RequestMapping(value = "/by-medicamento",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Receta>> listarRecetasPorMedicamento(
            @RequestParam Integer medicamentoId
    );

    @RequestMapping(value = "/guardar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> guardarReceta(
            @RequestBody RecetaRq receta
    ) throws BadRequestException;

    @PostMapping("/actualizar")
    ResponseEntity<RespuestaRs> actualizarReceta(
                @RequestBody RecetaRq receta
        ) throws BadRequestException;
}
