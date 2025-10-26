package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.RecetaApi;
import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.RecetaService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author AORUS
 */
@RestController
public class RecetaApiController implements RecetaApi {

    @Autowired
    private RecetaService recetaService;

    @Override
    public ResponseEntity<List<Receta>> listarTodasLasRecetas() {
        return ResponseEntity.ok(this.recetaService.listarTodasLasRecetas());
    }

    @Override
    public ResponseEntity<List<Receta>> listarRecetasPorCita(Integer citaId) {
        return ResponseEntity.ok(this.recetaService.listarRecetasPorCita(citaId));
    }

    @Override
    public ResponseEntity<List<Receta>> listarRecetasPorMedicamento(Integer medicamentoId) {
        return ResponseEntity.ok(this.recetaService.listarRecetasPorMedicamento(medicamentoId));
    }

    @Override
    public ResponseEntity<RespuestaRs> guardarReceta(RecetaRq receta) throws BadRequestException {
        return ResponseEntity.ok(this.recetaService.guardarReceta(receta));
    }

    @Override
    public ResponseEntity<RespuestaRs> actualizarReceta(RecetaRq receta) throws BadRequestException {
        return ResponseEntity.ok(this.recetaService.actualizarReceta(receta));
    }
}
