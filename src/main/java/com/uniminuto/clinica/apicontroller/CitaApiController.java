package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.CitaApi;
import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.model.CitaRs;
import com.uniminuto.clinica.service.CitaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CitaApiController implements CitaApi {

    private final CitaService citaService;

    public CitaApiController(CitaService citaService) {
        this.citaService = citaService;
    }

    @Override
    public ResponseEntity<Cita> crearCita(CitaRs citaRs) {
        return ResponseEntity.ok(citaService.guardarCita(citaRs));
    }

    @Override
    public ResponseEntity<List<Cita>> listarCitas(){
        List<Cita> citas = citaService.listarCitas();
        return ResponseEntity.ok(citas);
    }

}
