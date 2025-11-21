package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.AuditoriaApi;
import com.uniminuto.clinica.model.AuditoriaRs;
import com.uniminuto.clinica.service.AuditoriaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class AuditoriaApiController implements AuditoriaApi {
    private final AuditoriaService auditoriaService;
    @Override
    public ResponseEntity<List<AuditoriaRs>> filtrar(String username, String tipo, String desde, String hasta) {
        List<AuditoriaRs> data = auditoriaService.filtrar(username, tipo, desde, hasta);

        return ResponseEntity.ok(data);
    }
}
