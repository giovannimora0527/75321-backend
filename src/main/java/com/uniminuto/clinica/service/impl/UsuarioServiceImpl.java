package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.UsuarioRq;
import com.uniminuto.clinica.model.UsuarioRs;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.repository.UsuarioRepository;
import com.uniminuto.clinica.service.UsuarioService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
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
        dto.setEmail(usuario.getEmail());



        return dto;
    }

    @Override
    public RespuestaRs guardarUsuario(UsuarioRq UsuarioNuevo) throws BadRequestException {
        //Validar campos
        this.isCamposObligatorios(UsuarioNuevo);

        //validar que el usuario exista por el nombre
        if(this.usuarioRepository
                .existsByUsername(UsuarioNuevo.getUsername().toLowerCase())){
            throw  new BadRequestException("El usuario ya existe.Intente de nuevo");
        }
        if (usuarioRepository.existsByEmail(UsuarioNuevo.getEmail().toLowerCase().trim())) {
            throw new BadRequestException("El email ya existe. Intente con otro.");
        }

        //Si no existe creo el usuario y lo guardo
        Usuario nuevo=new Usuario();
        nuevo.setUsername(UsuarioNuevo.getUsername().toLowerCase());
        nuevo.setFechaCreacion(LocalDateTime.now());
        nuevo.setRol(UsuarioNuevo.getRol().toUpperCase());
        nuevo.setPasswordHash(this.convertirAHash(UsuarioNuevo.getPassword()));
        nuevo.setActivo(true);
        //Validamos
        nuevo.setEmail( UsuarioNuevo.getEmail().toLowerCase().trim() );
        //Guardar en la base de datos
        this.usuarioRepository.save(nuevo);

        //Devolvemos Una respuesta
        RespuestaRs respuesta=new RespuestaRs();
        respuesta.setStatus(200);
        respuesta.setMensaje("El usuario se guardado con exito");
        return respuesta;

    }

    @Override
    public RespuestaRs actualizarPorUsername(String username, UsuarioRq cambios) throws BadRequestException {
        return null;
        //consultar si existe el usuario

        //devolver Respuesta actualizada

    }




    // Metodo Privado Para Cifrar las Contraseñas
    private String convertirAHash(String textoAConvertir) {
        String algoritmo = "MD5";
        try {
            MessageDigest md = MessageDigest.getInstance(algoritmo);
            byte[] hashBytes = md.digest(textoAConvertir.getBytes());

            // Convertir a hexadecimal
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algoritmo no soportado: "
                    + algoritmo, e);
        }

    }

    //Metodo de Validacion de Flujo
    private void isCamposObligatorios(UsuarioRq usuarioNuevo)
            throws BadRequestException {
        if (usuarioNuevo.getUsername() == null
                || usuarioNuevo.getUsername().isBlank()
                | usuarioNuevo.getUsername().isEmpty()) {
            throw new BadRequestException("El campo username es obligatorio");
        }
        if (usuarioNuevo.getPassword() == null
                || usuarioNuevo.getPassword().isBlank()
                | usuarioNuevo.getPassword().isEmpty()) {
            throw new BadRequestException("El campo password es obligatorio");
        }

        if (usuarioNuevo.getRol() == null
                || usuarioNuevo.getRol().isBlank()
                | usuarioNuevo.getRol().isEmpty()) {
            throw new BadRequestException("El campo rol es obligatorio");
        }
    }

}

