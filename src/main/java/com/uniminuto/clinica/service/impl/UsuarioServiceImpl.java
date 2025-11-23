package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.PasswordResetToken;
import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.UsuarioRq;
import com.uniminuto.clinica.repository.UsuarioRepository;
import com.uniminuto.clinica.repository.PasswordResetTokenRepository;
import com.uniminuto.clinica.service.UsuarioService;
import com.uniminuto.clinica.service.EmailService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio de usuarios
 * @author lmora
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public List<Usuario> listarTodosLosUsuarios() {
        return this.usuarioRepository.findAll();
    }
    
    @Override
    public Usuario buscarUsuarioPorNombre(String nombre) throws BadRequestException {
        Optional<Usuario> optUser = this.usuarioRepository.findByUsername(nombre);
        if (!optUser.isPresent()) {
            throw new BadRequestException("No existe el usuario");
        }
        return optUser.get();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Usuario> buscarPorRol(String role) {
        List<Usuario> listUsers = this.usuarioRepository.findByRol(role);
        // Corrección del warning: usar lista genérica correctamente
        return !listUsers.isEmpty() ? listUsers : new ArrayList<>();
    }

    @Override
    public RespuestaRs guardarUsuario(UsuarioRq usuarioNuevo) throws BadRequestException {
        // Paso 1: validar que los campos obligatorios vengan con datos
        this.isCamposObligatorios(usuarioNuevo);
        
        // Paso 2: validar si el usuario existe por nombre
        if (this.usuarioRepository.existsByUsername(usuarioNuevo.getUsername().toLowerCase())) {
            throw new BadRequestException("El usuario ya existe. Intente de nuevo.");
        }
        
        // Si no existe, lo creo y lo guardo
        Usuario nuevo = new Usuario();
        nuevo.setUsername(usuarioNuevo.getUsername().toLowerCase());
        nuevo.setFechaCreacion(LocalDateTime.now());
        nuevo.setRol(usuarioNuevo.getRol().toUpperCase());
        nuevo.setPassword(this.convertirAHash(usuarioNuevo.getPassword()));
        nuevo.setActivo(true);
        
        // Inicializar campos de auditoría y bloqueo
        nuevo.setIntentosFallidos(0);
        nuevo.setBloqueado(false);
        nuevo.setRequiereCambioPassword(false);
        
        this.usuarioRepository.save(nuevo);
        
        // Devolver respuesta exitosa
        RespuestaRs respuesta = new RespuestaRs();
        respuesta.setStatus(200);
        respuesta.setMensaje("El usuario se ha guardado con éxito.");
        return respuesta;
    }
    
    @Override
    public RespuestaRs actualizarUsuario(UsuarioRq usuario) throws BadRequestException {
        // Implementación de actualizar un usuario existente
        Optional<Usuario> optUser = this.usuarioRepository.findById(usuario.getId());
        if (optUser.isEmpty()) {
            throw new BadRequestException("El usuario no existe. No se puede actualizar");
        }
        
        Usuario userActual = optUser.get();
        
        // Validar si el username cambió y si ya existe
        if (!userActual.getUsername().equals(usuario.getUsername())) {
            Optional<Usuario> optUserByName = this.usuarioRepository
                    .findByUsername(usuario.getUsername());
            if (optUserByName.isPresent()) {
                throw new BadRequestException("El nombre de usuario ya existe. No se puede actualizar");
            }
        }
        
        userActual.setUsername(usuario.getUsername());
        userActual.setPassword(this.convertirAHash(usuario.getPassword()));
        userActual.setRol(usuario.getRol());
        userActual.setActivo(usuario.getActivo());
        this.usuarioRepository.save(userActual);
        
        RespuestaRs rta = new RespuestaRs();
        rta.setStatus(200);
        rta.setMensaje("Se ha actualizado el usuario con éxito.");
        return rta;
    }
    
    // ============================================
    // MÉTODOS PARA RECUPERACIÓN DE CONTRASEÑA
    // ============================================
    
    @Override
    public String generarTokenRecuperacion(String email) throws BadRequestException {
        Usuario usuario = buscarPorEmail(email);
        String token = UUID.randomUUID().toString();
        LocalDateTime fechaExpiracion = LocalDateTime.now().plusHours(24);
        
        PasswordResetToken resetToken = new PasswordResetToken(token, usuario, fechaExpiracion);
        passwordResetTokenRepository.save(resetToken);

        System.out.println("Generando token de recuperación para: " + email);
        
        try {
            // CORREGIDO: Usar el método correcto del EmailService
            emailService.enviarEmailRecuperacion(email, token);
            System.out.println("Email de recuperación enviado exitosamente");
        } catch (Exception e) {
            System.err.println("Error al enviar email: " + e.getMessage());
            e.printStackTrace();
            throw new BadRequestException("Error al enviar el email de recuperación");
        }
        
        return token;
    }

    @Override
    public RespuestaRs validarYResetearContrasena(String token, String newPassword) throws BadRequestException {
        Optional<PasswordResetToken> tokenOptional = passwordResetTokenRepository.findByToken(token);
        if (!tokenOptional.isPresent()) {
            throw new BadRequestException("Token inválido");
        }

        PasswordResetToken resetToken = tokenOptional.get();

        if (resetToken.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Token expirado. Solicita uno nuevo.");
        }

        if (resetToken.isUtilizado()) {
            throw new BadRequestException("Token ya fue utilizado");
        }

        Usuario usuario = resetToken.getUsuario();
        String passwordHasheada = convertirAHash(newPassword);
        usuario.setPassword(passwordHasheada);
        
        // Limpiar campos de bloqueo al resetear contraseña
        usuario.setIntentosFallidos(0);
        usuario.setBloqueado(false);
        usuario.setFechaBloqueo(null);
        
        usuarioRepository.save(usuario);

        resetToken.setUtilizado(true);
        passwordResetTokenRepository.save(resetToken);

        System.out.println("✅ Contraseña actualizada para usuario: " + usuario.getUsername());

        RespuestaRs respuesta = new RespuestaRs();
        respuesta.setStatus(200);
        respuesta.setMensaje("Contraseña actualizada exitosamente");
        return respuesta;
    }

    @Override
    public Usuario buscarPorEmail(String email) throws BadRequestException {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (!usuarioOpt.isPresent()) {
            throw new BadRequestException("Usuario no encontrado con ese email");
        }
        return usuarioOpt.get();
    }

    // MÉTODOS PRIVADOS

    /**
     * Convierte texto a hash MD5
     */
    private String convertirAHash(String textoAConvertir) {
        String algoritmo = "MD5";
        try {
            MessageDigest md = MessageDigest.getInstance(algoritmo);
            byte[] hashBytes = md.digest(textoAConvertir.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algoritmo no soportado: " + algoritmo, e);
        }
    }

    /**
     * Valida que los campos obligatorios no estén vacíos
     */
    private void isCamposObligatorios(UsuarioRq usuarioNuevo) throws BadRequestException {
        if (usuarioNuevo.getUsername() == null || 
            usuarioNuevo.getUsername().isBlank() || 
            usuarioNuevo.getUsername().isEmpty()) {
            throw new BadRequestException("El campo username es obligatorio");
        }
        if (usuarioNuevo.getPassword() == null || 
            usuarioNuevo.getPassword().isBlank() || 
            usuarioNuevo.getPassword().isEmpty()) {
            throw new BadRequestException("El campo password es obligatorio");
        }
        if (usuarioNuevo.getRol() == null || 
            usuarioNuevo.getRol().isBlank() || 
            usuarioNuevo.getRol().isEmpty()) {
            throw new BadRequestException("El campo rol es obligatorio");
        }
    }
}
