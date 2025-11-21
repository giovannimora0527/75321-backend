package com.uniminuto.clinica.api;

import com.uniminuto.clinica.model.AuditoriaRs;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/auditoria")
public interface AuditoriaApi {

    @GetMapping("/filtrar")
    ResponseEntity<List<AuditoriaRs>> filtrar(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String desde,
            @RequestParam(required = false) String hasta
    );
}
