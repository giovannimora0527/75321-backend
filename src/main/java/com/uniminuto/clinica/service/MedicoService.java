package com.uniminuto.clinica.service;

import java.util.List;

import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.entity.Medico;
public interface MedicoService {
    List<Medico> listarTodo();
//Firma de funciones que puede hacer el Service, firma
    List<Medico> obtenerMedicosPorEspecializacion(String codigoEspecializacion);//Solo la firma
}
