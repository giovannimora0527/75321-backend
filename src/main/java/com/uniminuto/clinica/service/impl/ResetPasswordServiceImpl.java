package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.ResetPasswordToken;
import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.repository.ResetPasswordTokenRepository;
import com.uniminuto.clinica.repository.UsuarioRepository;
import com.uniminuto.clinica.service.CifrarService;
import com.uniminuto.clinica.service.ResetPasswordService;
import com.uniminuto.clinica.service.AuditLogService;
import com.uniminuto.clinica.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResetPasswordServiceImpl implements ResetPasswordService {
    @Autowired
    private CifrarService cifrarService;


    @Autowired
    private ResetPasswordTokenRepository tokenRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private EmailService emailService;

    @Autowired(required = false)
    private PasswordEncoder passwordEncoder;

    // configurable
    private final long TOKEN_EXPIRATION_MINUTES = 30L;

    @Override
    public String createTokenForUsername(String username, String clientIp) {
        Optional<Usuario> ou = usuarioRepository.findByEmail(username);
        if (ou.isEmpty()) {
            // registrar igualmente pero sin exponer al usuario
            auditLogService.record("PASSWORD_RESET_REQUEST", username, null, "Usuario no encontrado", clientIp);
            return null;
        }
        Usuario user = ou.get();

        // invalidar tokens previos (opcional)
        tokenRepository.findByUserIdAndUsadoFalse(user.getId()).ifPresent(t -> {
            t.setUsado(true);
            tokenRepository.save(t);
        });

        String token = UUID.randomUUID().toString();
        ResetPasswordToken rpt = new ResetPasswordToken();
        rpt.setUserId(user.getId());
        rpt.setToken(token);
        rpt.setFechaExpiracion(LocalDateTime.now().plusMinutes(TOKEN_EXPIRATION_MINUTES));
        tokenRepository.save(rpt);

        // Enviar email
        try {
            String link = "http://localhost:4200/reset-password?token=" + token;

            String html = "<p>Para restablecer su contraseña haga clic: <a href=\"" + link + "\">Restablecer contraseña</a></p>";
            // Ajusta el método de tu EmailService real si tiene otro nombre
            emailService.sendHtmlEmail(
                    user.getEmail(),
                    "Recuperación de contraseña",
                    html,
                    emailService.getTo() // usa EL MISMO remitente autenticado
            );

            auditLogService.record("PASSWORD_RESET_REQUEST", username, user.getId(), "Token generado y correo enviado", clientIp);
        } catch (Exception ex) {
            auditLogService.record("PASSWORD_RESET_REQUEST", username, user.getId(), "Token generado pero fallo envío: " + ex.getMessage(), clientIp);
        }

        // DEV: puedes devolver token para pruebas; en producción NO devolverlo
        return token;
    }

    @Override
    public boolean resetPasswordByToken(String token, String newPassword, String clientIp) {
        Optional<ResetPasswordToken> otr = tokenRepository.findByTokenAndUsadoFalse(token);
        if (otr.isEmpty()) {
            auditLogService.record("PASSWORD_RESET_FAILED", null, null, "Token inválido o usado", clientIp);
            return false;
        }
        ResetPasswordToken rpt = otr.get();
        if (rpt.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            auditLogService.record("PASSWORD_RESET_FAILED", null, rpt.getUserId(), "Token expirado", clientIp);
            return false;
        }

        Optional<Usuario> ou = usuarioRepository.findById(rpt.getUserId());
        if (ou.isEmpty()) {
            auditLogService.record("PASSWORD_RESET_FAILED", null, rpt.getUserId(), "Usuario no encontrado para token", clientIp);
            return false;
        }
        Usuario user = ou.get();

        // actualizar contraseña
        if (passwordEncoder != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
        } else {
            // si usas tu cifrarService en vez de PasswordEncoder, reemplazar aquí
            user.setPassword(cifrarService.encriptarPassword(newPassword));
        }
        usuarioRepository.save(user);

        // marcar token como usado
        rpt.setUsado(true);
        tokenRepository.save(rpt);

        auditLogService.record("PASSWORD_RESET_SUCCESS", user.getUsername(), user.getId(), "Contraseña actualizada mediante token", clientIp);
        return true;
    }
}
