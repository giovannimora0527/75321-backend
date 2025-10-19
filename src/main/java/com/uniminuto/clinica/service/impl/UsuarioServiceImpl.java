package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.UsuarioRq;
import com.uniminuto.clinica.repository.UsuarioRepository;
import com.uniminuto.clinica.service.UsuarioService;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
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
        return !listUsers.isEmpty() ? listUsers : Collections.EMPTY_LIST;
    }

    @Override
    public RespuestaRs guardarUsuario(UsuarioRq usuarioNuevo)
            throws BadRequestException {
        // Paso 1. validar que los campos que ingresen esten obligatorios 
        // o vengan con datos
        this.isCamposObligatorios(usuarioNuevo);

        // Paso validar si el usuario existe por nombre
        if (this.usuarioRepository
                .existsByUsername(usuarioNuevo.getUsername().toLowerCase())) {
            throw new BadRequestException("El usuario ya existe. Intente de nuevo.");
        }

        // Si no existe lo creo y lo guardo.
        Usuario nuevo = new Usuario();
        nuevo.setUsername(usuarioNuevo.getUsername()
                .toLowerCase());
        nuevo.setFechaCreacion(LocalDateTime.now());
        nuevo.setRol(usuarioNuevo.getRol().toUpperCase());
        nuevo.setPassword(this.convertirAHash(usuarioNuevo.getPassword()));
        nuevo.setActivo(true);
        this.usuarioRepository.save(nuevo);

        // Devuelvo una respuesta que el usuario fue guardado
        RespuestaRs respuesta = new RespuestaRs();
        respuesta.setStatus(200);
        respuesta.setMensaje("El usuario se ha guardado con exito.");
        return respuesta;
    }

    @Override
    public RespuestaRs actualizarUsuario(UsuarioRq usuario) throws BadRequestException {
        /**
         * TODO - Hacer la implementacion de actualizar un usuario existente.
         */
        return null;
    }

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
