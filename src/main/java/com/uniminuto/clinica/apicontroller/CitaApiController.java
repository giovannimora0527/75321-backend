package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.CitaApi;
import com.uniminuto.clinica.model.CitaRs;
import com.uniminuto.clinica.model.CrearCitaRq;
import com.uniminuto.clinica.service.CitaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CitaApiController implements CitaApi {
    private final CitaService citaService;
    @Override
    public CitaRs crear(CrearCitaRq rq) {
        return citaService.crear(rq);
    }
}
