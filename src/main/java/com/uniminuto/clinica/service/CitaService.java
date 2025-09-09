package com.uniminuto.clinica.service;

import com.uniminuto.clinica.model.CitaRs;
import com.uniminuto.clinica.model.CrearCitaRq;

/**
 *
 * @author lmora
 */
public interface CitaService {
    CitaRs crear(CrearCitaRq rq);//Firmamos el Metodo con el dto
    
}
