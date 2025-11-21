package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Especializacion;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 * @author jorge
 */
public interface EspecializacionService {
    
    List<Especializacion> listarTodo();
    
    Especializacion obtenerPorCodigo(String codigoEspecializacion) throws BadRequestException;
    
    Especializacion crear(Especializacion especializacion) throws BadRequestException;
    
    Especializacion actualizar(Long id, Especializacion especializacion) throws BadRequestException;
}
