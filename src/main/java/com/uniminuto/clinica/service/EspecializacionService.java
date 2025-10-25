package com.uniminuto.clinica.service;

import com.uniminuto.clinica.model.CrearEspecializacionRq;
import com.uniminuto.clinica.model.EspecializacionRs;

import java.util.List;

public interface EspecializacionService {
    //Firma de Crear Especializacion
    EspecializacionRs crear(CrearEspecializacionRq rq);

    //Listar unicamente las especilidades
    List<EspecializacionRs> listar();

    //Actualizar especializacion
    EspecializacionRs actualizar(Long id, CrearEspecializacionRq rq);
}
