package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.RecetaApi;
import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.RecetaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class RecetaApiController implements RecetaApi {

    /**
     * Servicio de recetas médicas.
     */
    @Autowired
    private RecetaService recetaService;

    @Override
    public ResponseEntity<List<Receta>> listarRecetas() {
        return ResponseEntity.ok(this.recetaService.listarRecetas());
    }

    @Override
    public ResponseEntity<RespuestaRs> guardarReceta(@RequestBody @Valid RecetaRq recetaRq) throws BadRequestException {
        return ResponseEntity.ok(this.recetaService.guardarReceta(recetaRq));
    }

    @Override
    public ResponseEntity<RespuestaRs> actualizarReceta(RecetaRq recetaRq) throws BadRequestException {
        return ResponseEntity.ok(this.recetaService.actualizarReceta(recetaRq));
    }
}
