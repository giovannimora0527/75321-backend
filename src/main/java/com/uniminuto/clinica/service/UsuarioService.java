package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Usuario;
import java.util.List;

import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.UsuarioRq;
import com.uniminuto.clinica.model.UsuarioRs;
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

    //Firma del nuevo metodo que tenemos en UsuarioRepository
    UsuarioRs buscarUsuarioPorNumeroDocumento(String numeroDocumento);

    //nota: Pasar UsuarioService Impl ya que impl se compromete a implentar
    // lo metodos de esta interfaz

    //Guardar el Usuario
    RespuestaRs guardarUsuario(UsuarioRq UsuarioNuevo)throws BadRequestException;

    RespuestaRs actualizarPorUsername(String username,UsuarioRq cambios)throws BadRequestException;
}
