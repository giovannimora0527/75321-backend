package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.UsuarioRq;
import com.uniminuto.clinica.repository.UsuarioRepository;
import com.uniminuto.clinica.service.CifrarService;
import com.uniminuto.clinica.service.EmailService;
import com.uniminuto.clinica.service.UsuarioService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

/**
 *
 * @author lmora
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private CifrarService cifrarService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> listarTodosLosUsuarios() {
        return this.usuarioRepository.findAll();
    }

    @Override
    public List<Usuario> encontrarPorRol(String rol) {
        return this.usuarioRepository.findByRol(rol);
    }

    @Override
    public Usuario encontrarPorNombre(String nombreUsuario)
            throws BadRequestException {
        Optional<Usuario> optUser = this.usuarioRepository
                .findByUsername(nombreUsuario);
        if (!optUser.isPresent()) {
            throw new BadRequestException("No existe el usuario");
        }

        return optUser.get();
    }

    @Override
    public List<Usuario> buscarPorEstado(Integer estado) {
        boolean activo = estado == 1 ? true : false;
        return this.usuarioRepository.findByActivo(activo);
    }

    @Override
    public RespuestaRs guardarUsuario(UsuarioRq usuarioNuevo)
            throws BadRequestException, MessagingException {
        // Paso 1. Validar que los campos llegue bien
        this.validarCampos(usuarioNuevo);
        // Paso 2. Consulto si existe el usuario por username
        Optional<Usuario> optUser = this.usuarioRepository
                .findByUsername(usuarioNuevo.getUsername().toLowerCase());
        if (optUser.isPresent()) {
            // Paso 3. Si existe lanzo error que ya existe el usuario
            throw new BadRequestException("El usuario ya existe.");
        }
        // Paso 4. Creo el usuario y seteo los campos que lleguen del post
        Usuario nuevo = new Usuario();
        nuevo.setActivo(true);
        nuevo.setFechaCreacion(LocalDateTime.now());
        nuevo.setRol(usuarioNuevo.getRol().toUpperCase());
        nuevo.setUsername(usuarioNuevo.getUsername().toLowerCase());
        nuevo.setEmail(usuarioNuevo.getEmail());

        nuevo = this.usuarioRepository.save(nuevo);
        String password = generarPass();
        nuevo.setPassword(this.cifrarService.encriptarPassword(password));
        this.usuarioRepository.save(nuevo);
        String html = String.format("""
                        <html>
                        <body>
                            <h2>¡Bienvenido a la Clínica Uniminuto!</h2>
                            <p>Hola <b>%s</b>,</p>
                            <p>Tu cuenta ha sido creada exitosamente.</p>
                            <p><b>Usuario:</b> %s</p>
                            <p><b>Correo:</b> %s</p>
                            <p><b>Contraseña temporal:</b> %s</p>
                            <p>Por favor, inicia sesión y cambia tu contraseña lo antes posible.</p>
                            <br>
                            <p>Si tienes alguna duda, responde a este correo.</p>
                            <hr>
                            <small>Este mensaje fue generado automáticamente, por favor no respondas a este correo.</small>
                        </body>
                        </html>
                        """,
                nuevo.getUsername(),
                nuevo.getUsername(),
                nuevo.getEmail(),
                password
        );

        this.emailService.sendHtmlEmail(
                nuevo.getEmail(),
                "Envio de contraseña",
                html,
                emailService.getTo()
        );


        // Paso 5. Devuelve respuesta ok
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("El usuario se ha guardado correctamente.");
        rta.setStatus(200);
        return rta;
    }

    /**
     * Genera una contraseña aleatoria de 8 caracteres.
     *
     * @return Contraseña generada.
     */
    private String generarPass() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        int longitudDeseada = 8;

        for (int i = 0; i < longitudDeseada; i++) {
            int indiceAleatorio = (int) (Math.random() * caracteres.length());
            password.append(caracteres.charAt(indiceAleatorio));
        }

        return password.toString();
    }

    @Override
    public RespuestaRs actualizarUsuario(UsuarioRq usuarioNuevo) throws BadRequestException {
        // Paso 1. Verificar que el ID del usuario venga y pertenezca a un usuario
        Optional<Usuario> optUser = this.usuarioRepository.findById(usuarioNuevo.getId());
        if (optUser.isEmpty()) {
            throw new BadRequestException("El ID del usuario no existe.");
        }

        Usuario userActualizar = optUser.get();
        if (!userActualizar.getUsername().toLowerCase()
                .equals(usuarioNuevo.getUsername().toLowerCase())) {
            // Cambio el nombre de usuario
            Optional<Usuario> optUserName = this.usuarioRepository
                    .findByUsername(usuarioNuevo.getUsername().toLowerCase());
            if (optUserName.isPresent()) {
                throw new BadRequestException("El nombre de usuario ya existe.");
            }
        }

        this.validarCampos(usuarioNuevo);

        userActualizar.setUsername(usuarioNuevo.getUsername().toLowerCase());
        userActualizar.setPassword(this.cifrarService.encriptarPassword(usuarioNuevo.getPassword()));
        userActualizar.setActivo(usuarioNuevo.isActivo());
        userActualizar.setRol(usuarioNuevo.getRol());
        userActualizar.setEmail(usuarioNuevo.getEmail());
        this.usuarioRepository.save(userActualizar);

        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("El usuario se ha actualizado correctamente.");
        rta.setStatus(200);
        return rta;
    }

    private void validarCampos(UsuarioRq usuarioNuevo)
            throws BadRequestException {
        if (usuarioNuevo.getUsername() == null
                || usuarioNuevo.getUsername().isBlank()
                || usuarioNuevo.getUsername().isEmpty()) {
            throw new BadRequestException("El campo username es obligatorio.");
        }
        if (usuarioNuevo.getEmail() == null
                || usuarioNuevo.getEmail().isBlank()
                || usuarioNuevo.getEmail().isEmpty()) {
            throw new BadRequestException("El campo email es obligatorio.");
        }
        if (usuarioNuevo.getRol() == null
                || usuarioNuevo.getRol().isBlank()
                || usuarioNuevo.getRol().isEmpty()) {
            throw new BadRequestException("El campo rol es obligatorio.");
        }
    }
}
