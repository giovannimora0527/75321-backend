package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Especializacion;

import java.util.List;

public interface EspecializacionService {

    List<Especializacion> listarTodo();

    Especializacion buscarPorCodigo(String codigo);

    Especializacion crear(Especializacion especializacion);

    Especializacion actualizar(Long id, Especializacion especializacion);
}
