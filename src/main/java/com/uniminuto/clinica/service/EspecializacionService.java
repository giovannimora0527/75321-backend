package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.model.EspecializacionRq;
import com.uniminuto.clinica.model.RespuestaRs;

import java.util.List;

public interface EspecializacionService {

    List<Especializacion> listarEspecializaciones();

    RespuestaRs guardarEspecializacion(EspecializacionRq rq);
}
