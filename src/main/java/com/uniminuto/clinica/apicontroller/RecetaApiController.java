package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.RecetaApi;
import com.uniminuto.clinica.model.CrearRecetaRq;
import com.uniminuto.clinica.model.RecetaRs;
import com.uniminuto.clinica.service.RecetaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RecetaApiController implements RecetaApi {
    private final RecetaService recetaService;

    @Override
    public RecetaRs crear(CrearRecetaRq rq) {
        return recetaService.crear(rq);
    }
}
