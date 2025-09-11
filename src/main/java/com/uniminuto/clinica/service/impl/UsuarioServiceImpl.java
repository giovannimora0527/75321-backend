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
 *
 * @author lmora
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> listarTodosLosUsuarios() {
        return this.usuarioRepository.findAll();
    }

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

    @Override
    public List<Usuario> buscarPorRol(String role) {
        if (role == null || role.trim().isEmpty()) {
            return this.usuarioRepository.findAll();
        }
        return this.usuarioRepository.findByRol(role.trim());
    }

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
