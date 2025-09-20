package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.RespuestaRs;

import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author lmora
 */
public interface UsuarioService {
    List<Usuario> listarTodosLosUsuarios();
    
    Usuario buscarUsuarioPorNombre(String nombre) 
            throws BadRequestException;
    
    List<Usuario> buscarPorRol(String role);

    RespuestaRs guardarUsuario();
}
