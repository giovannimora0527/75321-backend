package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.AutenticatorRs;
import com.uniminuto.clinica.model.AuthenticatorRq;
import com.uniminuto.clinica.repository.UsuarioRepository;
import com.uniminuto.clinica.service.AutenticarService;
import com.uniminuto.clinica.service.CifrarService;
import com.uniminuto.clinica.service.AuditLogService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.uniminuto.clinica.entity.Session;
import com.uniminuto.clinica.repository.SessionRepository;
import com.uniminuto.clinica.security.JwtUtil;

import javax.transaction.Transactional;

@Service
public class AutenticarServiceImpl implements AutenticarService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired(required = false)
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CifrarService cifrarService;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private AuditLogService auditLogService;

    // Parámetros configurables (puedes llevarlos a application.properties)
    private final int MAX_FAILED_ATTEMPTS = 3;
    private final long LOCK_DURATION_SECONDS = 300; // 5 minutos

    @Override
    @Transactional
    public AutenticatorRs autenticar(AuthenticatorRq request)
            throws BadRequestException {

        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(request.getUsername());
        String clientUsername = request.getUsername();
        String clientIp = "unknown"; // si tienes acceso a request, pásala desde controller

        if (usuarioOpt.isEmpty()) {
            // Registrar intento con username no encontrado (sin decirle al cliente)
            auditLogService.record("LOGIN_FAILED", clientUsername, null, "Usuario no encontrado", clientIp);
            throw new BadRequestException("Usuario o contraseña incorrectos");
        }
        Usuario usuario = usuarioOpt.get();

        // Verificar bloqueo temporal
        if (usuario.getBloqueadoHasta() != null && usuario.getBloqueadoHasta().isAfter(LocalDateTime.now())) {
            auditLogService.record("LOGIN_BLOCKED", usuario.getUsername(), usuario.getId(),
                    "Usuario bloqueado temporalmente hasta " + usuario.getBloqueadoHasta(), clientIp);

            throw new BadRequestException(
                    "{\"bloqueadoHasta\": \"" + usuario.getBloqueadoHasta().toString() + "\"}"
            );
        }

        boolean passwordOk;
        if (passwordEncoder != null) {
            passwordOk = passwordEncoder.matches(request.getPassword(), usuario.getPassword());
        } else {
            passwordOk = usuario.getPassword().equals(this.cifrarService.encriptarPassword(request.getPassword()));
        }

        if (!passwordOk) {
            Integer failed = usuario.getIntentosFallidos() == null ? 0 : usuario.getIntentosFallidos();
            failed++;
            usuario.setIntentosFallidos(failed);

            if (failed >= MAX_FAILED_ATTEMPTS) {
                usuario.setBloqueadoHasta(LocalDateTime.now().plusSeconds(LOCK_DURATION_SECONDS));
                auditLogService.record("ACCOUNT_LOCKED", usuario.getUsername(), usuario.getId(),
                        "Bloqueo por intentos fallidos", clientIp);
                usuarioRepository.save(usuario);

                AutenticatorRs rta = new AutenticatorRs();
                rta.setMensaje("Usuario bloqueado temporalmente");
                rta.setBloqueadoHasta(usuario.getBloqueadoHasta().toString());
                rta.setToken(null);

                return rta; // ahora siempre devuelve AutenticatorRs
            } else {
                auditLogService.record("LOGIN_FAILED", usuario.getUsername(), usuario.getId(),
                        "Contraseña incorrecta", clientIp);
                usuarioRepository.save(usuario);

                AutenticatorRs rta = new AutenticatorRs();
                rta.setMensaje("Contraseña incorrecta. Intentos restantes: " + (MAX_FAILED_ATTEMPTS - failed));
                rta.setToken(null);
                return rta;
            }
        }
        if (!passwordOk) {
            Integer failed = usuario.getIntentosFallidos() == null ? 0 : usuario.getIntentosFallidos();
            failed++;
            usuario.setIntentosFallidos(failed);

            if (failed >= MAX_FAILED_ATTEMPTS) {
                usuario.setBloqueadoHasta(LocalDateTime.now().plusSeconds(LOCK_DURATION_SECONDS));
                auditLogService.record("ACCOUNT_LOCKED", usuario.getUsername(), usuario.getId(),
                        "Bloqueo por intentos fallidos", clientIp);
                usuarioRepository.save(usuario);

                AutenticatorRs rta = new AutenticatorRs();
                rta.setMensaje("Usuario bloqueado temporalmente");
                rta.setBloqueadoHasta(usuario.getBloqueadoHasta().toString());
                rta.setToken(null);

                return rta; // ahora siempre devuelve AutenticatorRs
            } else {
                auditLogService.record("LOGIN_FAILED", usuario.getUsername(), usuario.getId(),
                        "Contraseña incorrecta", clientIp);
                usuarioRepository.save(usuario);

                AutenticatorRs rta = new AutenticatorRs();
                rta.setMensaje("Contraseña incorrecta. Intentos restantes: " + (MAX_FAILED_ATTEMPTS - failed));
                rta.setToken(null);
                return rta;
            }
        }


        // Login correcto: resetear contadores y bloqueo
        usuario.setIntentosFallidos(0);
        usuario.setBloqueadoHasta(null);
        usuarioRepository.save(usuario);
        auditLogService.record("LOGIN_SUCCESS", usuario.getUsername(), usuario.getId(), "Inicio de sesión exitoso", clientIp);

        // Generar y devolver un JWT
        AutenticatorRs rta = new AutenticatorRs();
        String token = jwtUtil.generateToken(usuario);
        rta.setToken(token);

        // Creamos la sesión del usuario autenticado
        crearSesionUsuario(usuario, token);
        return rta;
    }

    /**
     * Crea y almacena la sesión del usuario autenticado.
     *
     * @param usuario Usuario autenticado
     * @param token   Token JWT generado
     */
    private void crearSesionUsuario(Usuario usuario, String token) {
        // Elimina cualquier sesión previa del usuario
        sessionRepository.deleteByUserId(usuario.getId().intValue());
        Session session = new Session();
        session.setUserId(usuario.getId().intValue());
        session.setToken(token);
        session.setFechaIniSesion(LocalDateTime.now());
        Date fechaExpiracion = jwtUtil.getExpirationDateFromToken(token);
        session.setFechaExpiracion(fechaExpiracion.toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDateTime());
        sessionRepository.save(session);
    }
}
