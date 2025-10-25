package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.repository.UsuarioRepository;
import com.uniminuto.clinica.service.UsuarioService;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio para la lógica de negocio de usuarios.
 * Proporciona operaciones de consulta, búsqueda y filtrado de usuarios del sistema.
 *
 * @author lmora
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Lista todos los usuarios registrados en el sistema.
     * No aplica filtros ni criterios de ordenamiento específicos.
     *
     * @return lista completa de usuarios registrados, vacía si no hay usuarios
     */
    @Override
    public List<Usuario> listarTodosLosUsuarios() {
        return this.usuarioRepository.findAll();
    }

    /**
     * Busca un usuario específico por su nombre de usuario.
     * Valida que el nombre no sea nulo o vacío antes de realizar la búsqueda.
     *
     * @param nombre nombre de usuario a buscar, no puede ser nulo o vacío
     * @return usuario encontrado con el nombre especificado
     * @throws BadRequestException si el nombre es nulo, vacío o no se encuentra usuario
     */
    @Override
    public Usuario buscarUsuarioPorNombre(String nombre)
            throws BadRequestException {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new BadRequestException("El nombre de usuario es requerido");
        }

        Optional<Usuario> usuario = this.usuarioRepository
                .findByUsername(nombre.trim());

        if (!usuario.isPresent()) {
            throw new BadRequestException("Usuario con nombre '"
                    + nombre + "' no encontrado");
        }

        return usuario.get();
    }

    /**
     * Busca usuarios filtrados por rol específico.
     * Si el rol es nulo o vacío, retorna todos los usuarios.
     *
     * @param role rol por el cual filtrar usuarios, puede ser nulo para obtener todos
     * @return lista de usuarios con el rol especificado, o todos si role es nulo/vacío
     */
    @Override
    public List<Usuario> buscarPorRol(String role) {
        if (role == null || role.trim().isEmpty()) {
            return this.usuarioRepository.findAll();
        }
        return this.usuarioRepository.findByRol(role.trim());
    }

    /**
     * Busca un usuario específico por su número de documento.
     * Valida que el documento no sea nulo o vacío antes de realizar la búsqueda.
     *
     * @param numeroDocumento número de documento del usuario a buscar, no puede ser nulo o vacío
     * @return usuario encontrado con el documento especificado
     * @throws BadRequestException si el documento es nulo, vacío o no se encuentra usuario
     */
    @Override
    public Usuario buscarUsuarioPorDocumento(String numeroDocumento)
            throws BadRequestException {

        if (numeroDocumento == null || numeroDocumento.trim().isEmpty()) {
            throw new BadRequestException("El número de documento es requerido");
        }

        Optional<Usuario> usuario = this.usuarioRepository
                .findByNumeroDocumento(numeroDocumento.trim());

        if (!usuario.isPresent()) {
            throw new BadRequestException("Usuario con documento "
                    + numeroDocumento + " no encontrado");
        }

        return usuario.get();
    }
}