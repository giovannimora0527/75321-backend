package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.ClinicaApi;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.ClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST principal del sistema de clínica.
 *
 * @author lmora
 */
@RestController
public class ClinicaApiController implements ClinicaApi {

    /**
     * Servicio principal de la clínica.
     */
    @Autowired
    private ClinicaService clinicaService;

    /**
     * Servicio de prueba del sistema.
     *
     * @return estado del servicio
     */
    @Override
    public ResponseEntity<RespuestaRs> testService() {
        return ResponseEntity.ok(this.clinicaService.test());
    }
}