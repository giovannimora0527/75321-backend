package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.EspecializacionApi;
import com.uniminuto.clinica.model.CrearEspecializacionRq;
import com.uniminuto.clinica.model.EspecializacionRs;
import com.uniminuto.clinica.service.EspecializacionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@AllArgsConstructor
public class EspecializacionApiController implements EspecializacionApi {
     private final EspecializacionService especializacionService;
    @Override
    public ResponseEntity<EspecializacionRs> crear(CrearEspecializacionRq rq) {
        return ResponseEntity.ok(especializacionService.crear(rq));

    }

    @Override
    public ResponseEntity<List<EspecializacionRs>> listar() {
        return ResponseEntity.ok(especializacionService.listar());
    }
}
