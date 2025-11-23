package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Auditoria;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/auditoria")
public interface AuditoriaApi {
    
    @GetMapping(value = "/listar", produces = "application/json")
    ResponseEntity<List<Auditoria>> listarAuditorias();
}
