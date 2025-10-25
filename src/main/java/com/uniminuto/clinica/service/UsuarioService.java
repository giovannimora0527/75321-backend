package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Usuario;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 * Servicio para la lógica de negocio de usuarios.
 *
 * @author lmora
 */
public interface UsuarioService {

    /**
     * Lista todos los usuarios del sistema.
     *
     * @return lista de usuarios
     */
    List<Usuario> listarTodosLosUsuarios();

    /**
     * Busca un usuario por nombre de usuario.
     *
     * @param nombre nombre de usuario
     * @return usuario encontrado
     * @throws BadRequestException si el nombre es inválido
     */
    Usuario buscarUsuarioPorNombre(String nombre)
            throws BadRequestException;

    /**
     * Busca usuarios por rol del sistema.
     *
     * @param role rol del usuario
     * @return lista de usuarios con el rol
     */
    List<Usuario> buscarPorRol(String role);

    /**
     * Busca un usuario por número de documento.
     *
     * @param numeroDocumento número de documento
     * @return usuario encontrado
     * @throws BadRequestException si el documento es inválido
     */
    Usuario buscarUsuarioPorDocumento(String numeroDocumento)
            throws BadRequestException;
}
