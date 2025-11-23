package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Auditoria;
import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.repository.AuditoriaRepository;
import com.uniminuto.clinica.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditoriaService {
    
    private static final Logger log = LoggerFactory.getLogger(AuditoriaService.class);
    
    @Autowired
    private AuditoriaRepository auditoriaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private HttpServletRequest request;
    
    // Configuración parametrizable desde application.properties
    @Value("${auditoria.intentos.maximos:3}")
    private int intentosMaximos;
    
    @Value("${auditoria.bloqueo.minutos:5}")
    private int minutosBloqueo;
    
    @Value("${auditoria.bloqueo.habilitado:true}")
    private boolean bloqueoHabilitado;
    
    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%";
    private static final int LONGITUD_PASSWORD = 12;
    private static final int HORAS_EXPIRACION = 24;
    
    /**
     * Registra un intento de login fallido en la base de datos
     */
    @Transactional
    public void registrarLoginFallido(String nombreUsuario, String descripcionError) {
        String direccionIp = obtenerDireccionIp();
        
        Auditoria auditoria = new Auditoria(nombreUsuario, descripcionError, direccionIp);
        auditoriaRepository.save(auditoria);
        
        log.warn("🔴 Login fallido registrado - Usuario: {}, IP: {}, Error: {}", 
                 nombreUsuario, direccionIp, descripcionError);
        
        // Incrementar contador de intentos fallidos del usuario
        actualizarIntentosUsuario(nombreUsuario);
    }
    
    /**
     * Actualiza el contador de intentos fallidos del usuario
     */
    @Transactional
    public void actualizarIntentosUsuario(String nombreUsuario) {
        // Ajusta el nombre del método según tu UsuarioRepository
        usuarioRepository.findByUsername(nombreUsuario).ifPresent(usuario -> {
            int intentosActuales = (usuario.getIntentosFallidos() != null) 
                ? usuario.getIntentosFallidos() : 0;
            
            usuario.setIntentosFallidos(intentosActuales + 1);
            
            // Si alcanza el máximo, bloquear usuario
            if (usuario.getIntentosFallidos() >= intentosMaximos && bloqueoHabilitado) {
                usuario.setBloqueado(true);
                usuario.setFechaBloqueo(LocalDateTime.now());
                
                log.error("🚫 Usuario {} BLOQUEADO por {} intentos fallidos consecutivos", 
                         nombreUsuario, intentosMaximos);
            }
            
            usuarioRepository.save(usuario);
        });
    }
    
    /**
     * Verifica si un usuario está bloqueado
     */
    public boolean usuarioEstaBloqueado(String nombreUsuario) {
        return usuarioRepository.findByUsername(nombreUsuario)
            .map(usuario -> {
                if (!bloqueoHabilitado || !Boolean.TRUE.equals(usuario.getBloqueado())) {
                    return false;
                }
                
                LocalDateTime fechaBloqueo = usuario.getFechaBloqueo();
                if (fechaBloqueo == null) {
                    return false;
                }
                
                // Verificar si ya pasó el tiempo de bloqueo
                LocalDateTime tiempoDesbloqueo = fechaBloqueo.plusMinutes(minutosBloqueo);
                if (LocalDateTime.now().isAfter(tiempoDesbloqueo)) {
                    // Desbloquear automáticamente
                    desbloquearUsuario(nombreUsuario);
                    return false;
                }
                
                return true;
            })
            .orElse(false);
    }
    
    /**
     * Desbloquea un usuario y reinicia el contador de intentos
     */
    @Transactional
    public void desbloquearUsuario(String nombreUsuario) {
        usuarioRepository.findByUsername(nombreUsuario).ifPresent(usuario -> {
            usuario.setBloqueado(false);
            usuario.setIntentosFallidos(0);
            usuario.setFechaBloqueo(null);
            usuarioRepository.save(usuario);
            
            log.info("✅ Usuario {} desbloqueado automáticamente", nombreUsuario);
        });
    }
    
    /**
     * Reinicia el contador de intentos fallidos cuando el login es exitoso
     */
    @Transactional
    public void reiniciarIntentosUsuario(String nombreUsuario) {
        usuarioRepository.findByUsername(nombreUsuario).ifPresent(usuario -> {
            usuario.setIntentosFallidos(0);
            usuario.setBloqueado(false);
            usuario.setFechaBloqueo(null);
            usuarioRepository.save(usuario);
        });
    }
    
    /**
     * Genera y envía una contraseña temporal al usuario
     */
    @Transactional
    public String generarYEnviarPasswordTemporal(String nombreUsuario) throws MessagingException {
        Usuario usuario = usuarioRepository.findByUsername(nombreUsuario)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + nombreUsuario));
        
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new RuntimeException("El usuario no tiene un correo electrónico registrado");
        }
        
        String passwordTemporal = generarPasswordAleatorio();
        
        usuario.setPasswordTemporal(passwordEncoder.encode(passwordTemporal));
        usuario.setFechaExpiracionTemporal(LocalDateTime.now().plusHours(HORAS_EXPIRACION));
        usuario.setRequiereCambioPassword(true);
        
        usuarioRepository.save(usuario);
        
        emailService.enviarPasswordTemporal(usuario.getEmail(), nombreUsuario, passwordTemporal);
        
        log.info("📧 Contraseña temporal generada y enviada a {} ({})", 
                 nombreUsuario, usuario.getEmail());
        
        return passwordTemporal;
    }
    
    /**
     * Valida si una contraseña temporal es válida y no ha expirado
     */
    public boolean validarPasswordTemporal(Usuario usuario, String passwordIngresado) {
        if (usuario.getPasswordTemporal() == null || usuario.getFechaExpiracionTemporal() == null) {
            return false;
        }
        
        if (LocalDateTime.now().isAfter(usuario.getFechaExpiracionTemporal())) {
            log.warn("⏰ Contraseña temporal expirada para usuario: {}", usuario.getUsername());
            return false;
        }
        
        return passwordEncoder.matches(passwordIngresado, usuario.getPasswordTemporal());
    }
    
    /**
     * Obtiene el historial de auditoría de un usuario
     */
    public List<Auditoria> obtenerHistorialUsuario(String nombreUsuario) {
        return auditoriaRepository.findByNombreUsuarioOrderByFechaHoraDesc(nombreUsuario);
    }
    
    /**
     * Obtiene el tiempo restante de bloqueo en minutos
     */
    public long obtenerTiempoBloqueoRestante(String nombreUsuario) {
        return usuarioRepository.findByUsername(nombreUsuario)
            .map(usuario -> {
                if (usuario.getFechaBloqueo() == null) {
                    return 0L;
                }
                
                LocalDateTime tiempoDesbloqueo = usuario.getFechaBloqueo().plusMinutes(minutosBloqueo);
                LocalDateTime ahora = LocalDateTime.now();
                
                if (ahora.isAfter(tiempoDesbloqueo)) {
                    return 0L;
                }
                
                return java.time.Duration.between(ahora, tiempoDesbloqueo).toMinutes();
            })
            .orElse(0L);
    }
    
    // === MÉTODOS PRIVADOS ===
    
    private String generarPasswordAleatorio() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(LONGITUD_PASSWORD);
        
        for (int i = 0; i < LONGITUD_PASSWORD; i++) {
            int indice = random.nextInt(CARACTERES.length());
            password.append(CARACTERES.charAt(indice));
        }
        
        return password.toString();
    }
    
    private String obtenerDireccionIp() {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        String remoteAddr = request.getRemoteAddr();
        return (remoteAddr != null) ? remoteAddr : "IP desconocida";
    }
}
