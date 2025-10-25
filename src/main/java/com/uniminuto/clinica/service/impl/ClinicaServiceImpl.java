package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.ClinicaService;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio principal para operaciones generales de la clínica.
 * Proporciona servicios de prueba y verificación del sistema.
 *
 * @author lmora
 */
@Service
public class ClinicaServiceImpl implements ClinicaService {

    /**
     * Servicio de prueba del sistema que verifica el funcionamiento básico.
     * Retorna una respuesta exitosa con mensaje confirmatorio.
     *
     * @return respuesta con mensaje de éxito, estado true y código HTTP 200
     */
    @Override
    public RespuestaRs test() {
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("El servicio de clinica esta funcionando MELO");
        rta.setEstaFuncionando(true);
        rta.setStatus(200);
        return rta;
    }
}