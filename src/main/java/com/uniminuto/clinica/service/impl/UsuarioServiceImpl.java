package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.repository.UsuarioRepository;
import com.uniminuto.clinica.service.UsuarioService;
import java.util.Collections;
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
        Optional<Usuario> optUser = this.usuarioRepository
                .findByUsername(nombre);
        if (!optUser.isPresent()) {
            throw new BadRequestException("No existe el usuario");
        }        
       return optUser.get();
    }

    @Override
    public List<Usuario> buscarPorRol(String role) {
        List<Usuario> listUsers = this.usuarioRepository.findByRol(role);
        return !listUsers.isEmpty()? listUsers : Collections.EMPTY_LIST;
    }

    @Override
    public RespuestaRs guardarUsuario() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
