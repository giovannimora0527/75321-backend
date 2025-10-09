package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface RecetaService {
    List<Receta> listarRecetasOrdenadas();

    RespuestaRs guardarReceta(RecetaRq recetaRq) throws BadRequestException;
}
