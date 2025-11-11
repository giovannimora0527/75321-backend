package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.UsuarioRq;
import java.util.List;
import org.apache.coyote.BadRequestException;

import javax.mail.MessagingException;

/**
 *
 * @author lmora
 */
public interface UsuarioService {
  List<Usuario> listarTodosLosUsuarios();
  
  List<Usuario> encontrarPorRol(String rol);
  
  Usuario encontrarPorNombre(String nombreUsuario) throws BadRequestException;
  
  List<Usuario> buscarPorEstado(Integer estado);
  
  RespuestaRs guardarUsuario(UsuarioRq usuarioNuevo) throws BadRequestException, MessagingException;

  RespuestaRs actualizarUsuario(UsuarioRq usuarioNuevo) throws BadRequestException;
}
