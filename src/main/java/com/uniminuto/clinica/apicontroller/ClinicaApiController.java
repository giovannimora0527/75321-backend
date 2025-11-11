package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.ClinicaApi;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.ClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lmora
 */
@RestController
public class ClinicaApiController implements ClinicaApi {

    @Autowired
    private ClinicaService clinicaService;

    @Override
    public ResponseEntity<RespuestaRs> testService() {
        return ResponseEntity.ok(clinicaService.testearApp());
    }

}
