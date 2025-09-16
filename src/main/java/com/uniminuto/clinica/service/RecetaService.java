package com.uniminuto.clinica.service;

import com.uniminuto.clinica.model.CrearRecetaRq;
import com.uniminuto.clinica.model.RecetaRs;

import java.util.List;

public interface RecetaService {
    //Listamos funcionalidades mas no logica

    RecetaRs crear(CrearRecetaRq rq);

    //Funcionalidad

    List<RecetaRs> listarRecientes();
}
