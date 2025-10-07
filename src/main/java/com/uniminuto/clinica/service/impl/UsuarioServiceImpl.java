package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.UsuarioRs;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.repository.UsuarioRepository;
import com.uniminuto.clinica.service.UsuarioService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lmora
 */
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final PacienteRepository pacienteRepository;
    private final UsuarioRepository usuarioRepository;


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
    public UsuarioRs buscarUsuarioPorNumeroDocumento(String numeroDocumento) {

        //Usamos el repository para buscar al paciente y su usuario
        Optional<Paciente>pacienteOpt=pacienteRepository.findByNumeroDocumento(numeroDocumento);

        //Excepcion con if
        if(pacienteOpt.isEmpty()||pacienteOpt.get().getUsuario()==null){
            return null;
        }
        Usuario usuario=pacienteOpt.get().getUsuario();

        //Mapeo manual del DTO UsuarioRs
        UsuarioRs dto=new UsuarioRs();
        dto.setId(usuario.getId());
        dto.setUsername(usuario.getUsername());
        dto.setRol(usuario.getRol());
        dto.setFechaCreacion(usuario.getFechaCreacion());
        dto.setActivo(usuario.isActivo());

        return dto;
    }

}
