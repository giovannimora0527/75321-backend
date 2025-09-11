package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Usuario;
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
    
    Usuario buscarUsuarioPorDocumento(String numeroDocumento)
            throws BadRequestException;
}