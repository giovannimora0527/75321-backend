package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.UsuarioRq;
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
    
    RespuestaRs guardarUsuario(UsuarioRq usuarioNuevo) 
            throws BadRequestException;

    RespuestaRs actualizarUsuario(UsuarioRq usuario)
            throws BadRequestException;
}
