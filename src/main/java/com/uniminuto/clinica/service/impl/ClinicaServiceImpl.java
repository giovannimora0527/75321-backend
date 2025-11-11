package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.ClinicaService;
import org.springframework.stereotype.Service;

/**
 *
 * @author lmora
 */
@Service
public class ClinicaServiceImpl implements ClinicaService {

    @Override
    public RespuestaRs testearApp() {
        RespuestaRs rta = new RespuestaRs();
        rta.setStatus(200);
        rta.setMensaje("Hola Mundo");
        return rta;
    }
    
}
