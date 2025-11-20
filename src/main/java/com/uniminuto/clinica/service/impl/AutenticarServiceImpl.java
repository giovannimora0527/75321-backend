package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.LogAuditoria; // Importar
import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.AutenticatorRs;
import com.uniminuto.clinica.model.AuthenticatorRq;
import com.uniminuto.clinica.repository.LogAuditoriaRepository; // Importar
import com.uniminuto.clinica.repository.UsuarioRepository;
import com.uniminuto.clinica.service.AutenticarService;
import com.uniminuto.clinica.service.CifrarService;
import com.uniminuto.clinica.service.EmailService; // Importar para enviar correos
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest; // Para obtener IP si es necesario
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID; // Para generar contraseña random

// ... otros imports (Session, SessionRepository, JwtUtil)

@Service
public class AutenticarServiceImpl implements AutenticarService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired(required = false)
    private PasswordEncoder passwordEncoder;

    @Autowired
    private com.uniminuto.clinica.security.JwtUtil jwtUtil; // Usa nombre completo si hay conflicto

    @Autowired
    private CifrarService cifrarService;

    @Autowired
    private com.uniminuto.clinica.repository.SessionRepository sessionRepository;

    // NUEVAS INYECCIONES
    @Autowired
    private LogAuditoriaRepository auditRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private HttpServletRequest httpRequest; // Para capturar la IP automáticamente

    @Override
    @Transactional
    public AutenticatorRs autenticar(AuthenticatorRq request) throws BadRequestException {
        String ip = httpRequest.getRemoteAddr(); // Capturar IP del cliente

        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(request.getUsername());

        // 1. Validar si usuario existe
        if (usuarioOpt.isEmpty()) {
            registrarLog(request.getUsername(), "Intento de inicio de sesión: Usuario no encontrado", ip, "LOGIN_FALLIDO");
            throw new BadRequestException("Usuario o contraseña incorrectos");
        }

        Usuario usuario = usuarioOpt.get();

        // 2. Validar Bloqueo (Requerimiento 2)
        if (usuario.getFechaDesbloqueo() != null && LocalDateTime.now().isBefore(usuario.getFechaDesbloqueo())) {
            // Registrar intento durante bloqueo
            registrarLog(usuario.getUsername(), "Intento de acceso durante bloqueo", ip, "ACCESO_BLOQUEADO");
            throw new BadRequestException("Usuario bloqueado temporalmente. Intente más tarde.");
        }

        // Si ya pasó el tiempo de bloqueo, limpiamos el bloqueo
        if (usuario.getFechaDesbloqueo() != null && LocalDateTime.now().isAfter(usuario.getFechaDesbloqueo())) {
            usuario.setFechaDesbloqueo(null);
            usuario.setIntentosFallidos(0);
            usuarioRepository.save(usuario);
        }

        // 3. Verificar contraseña
        boolean passwordOk;
        if (passwordEncoder != null) {
            passwordOk = passwordEncoder.matches(request.getPassword(), usuario.getPassword());
        } else {
            passwordOk = usuario.getPassword().equals(this.cifrarService.encriptarPassword(request.getPassword()));
        }

        if (!passwordOk) {
            manejarIntentoFallido(usuario, ip);
            throw new BadRequestException("Usuario o contraseña incorrectos");
        }

        // 4. Login Exitoso
        // Resetear contadores
        usuario.setIntentosFallidos(0);
        usuario.setFechaDesbloqueo(null);
        usuarioRepository.save(usuario);

        registrarLog(usuario.getUsername(), "Inicio de sesión exitoso", ip, "LOGIN_EXITOSO");

        AutenticatorRs rta = new AutenticatorRs();
        String token = jwtUtil.generateToken(usuario);
        rta.setToken(token);

        crearSesionUsuario(usuario, token);
        return rta;
    }

    // Método auxiliar para intentos fallidos (Req 2)
    private void manejarIntentoFallido(Usuario usuario, String ip) {
        int intentos = (usuario.getIntentosFallidos() == null) ? 0 : usuario.getIntentosFallidos();
        intentos++;
        usuario.setIntentosFallidos(intentos);

        registrarLog(usuario.getUsername(), "Contraseña incorrecta. Intento " + intentos, ip, "LOGIN_FALLIDO");

        // Si llega a 3 intentos, bloquear por 5 minutos
        if (intentos >= 3) {
            usuario.setFechaDesbloqueo(LocalDateTime.now().plusMinutes(5));
            registrarLog(usuario.getUsername(), "Usuario bloqueado por 3 intentos fallidos", ip, "BLOQUEO_USUARIO");
        }
        usuarioRepository.save(usuario);
    }

    // Método auxiliar para Logs
    private void registrarLog(String username, String descripcion, String ip, String tipo) {
        LogAuditoria log = new LogAuditoria();
        log.setFecha(LocalDateTime.now());
        log.setUsername(username);
        log.setDescripcion(descripcion);
        log.setIpAddress(ip);
        log.setTipoEvento(tipo);
        auditRepository.save(log);
    }

    // Implementación Recuperación de Contraseña (Req 1)
    @Override
    @Transactional
    public void recuperarContrasena(String username, String ipAddress) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);

        if (usuarioOpt.isEmpty()) {
            // REQ: Registrar log si usuario es inválido
            registrarLog(username, "Intento recuperación: Usuario no existe", ipAddress, "RECUPERACION_FALLIDA");
            // REQ: No mostrar error al usuario (seguridad), retornamos éxito falso
            return;
        }

        Usuario usuario = usuarioOpt.get();

        // Generar contraseña temporal
        String nuevaPass = UUID.randomUUID().toString().substring(0, 8); // 8 caracteres

        // Actualizar en BD
        if (passwordEncoder != null) {
            usuario.setPassword(passwordEncoder.encode(nuevaPass));
        } else {
            usuario.setPassword(cifrarService.encriptarPassword(nuevaPass));
        }
        usuarioRepository.save(usuario);

        registrarLog(username, "Generación de contraseña temporal", ipAddress, "RECUPERACION_EXITOSA");

        // Enviar Correo
        try {
            String asunto = "Recuperación de Contraseña - Clínica";
            String mensaje = "<h1>Hola " + usuario.getUsername() + "</h1>"
                    + "<p>Se ha solicitado recuperar su contraseña.</p>"
                    + "<p>Su contraseña temporal es: <strong>" + nuevaPass + "</strong></p>"
                    + "<p>Por favor ingrese y cámbiela inmediatamente.</p>";

            emailService.sendHtmlEmail(usuario.getEmail(), asunto, mensaje, "admin@clinica.edu.co"); // Ajusta el 'from'
        } catch (Exception e) {
            e.printStackTrace();
            // Podrías loguear el error de envío de correo aquí también
        }
    }

    // ... mantener el método crearSesionUsuario que ya tenías ...
    private void crearSesionUsuario(Usuario usuario, String token) {
        sessionRepository.deleteByUserId(usuario.getId().intValue());
        com.uniminuto.clinica.entity.Session session = new com.uniminuto.clinica.entity.Session();
        session.setUserId(usuario.getId().intValue());
        session.setToken(token);
        session.setFechaIniSesion(LocalDateTime.now());
        java.util.Date fechaExpiracion = jwtUtil.getExpirationDateFromToken(token);
        session.setFechaExpiracion(fechaExpiracion.toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDateTime());
        sessionRepository.save(session);
    }
}
