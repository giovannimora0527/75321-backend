package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.AuditoriaApi;
import com.uniminuto.clinica.entity.Auditoria;
import com.uniminuto.clinica.repository.AuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class AuditoriaApiController implements AuditoriaApi {

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    @Override
    public ResponseEntity<List<Auditoria>> listarAuditorias() {
        try {
            List<Auditoria> auditorias = auditoriaRepository.findAll();
            return ResponseEntity.ok(auditorias);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
