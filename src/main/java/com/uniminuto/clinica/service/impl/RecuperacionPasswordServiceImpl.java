package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.RecuperarPasswordRq;
import com.uniminuto.clinica.repository.UsuarioRepository;
import com.uniminuto.clinica.service.AuditoriaService;
import com.uniminuto.clinica.service.EmailService;
import com.uniminuto.clinica.service.RecuperacionPasswordService;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import javax.mail.MessagingException;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RecuperacionPasswordServiceImpl implements RecuperacionPasswordService {
    //inyectamos
    private final UsuarioRepository usuarioRepository;
    private final AuditoriaService auditoriaService;
    private final EmailService emailService;
    private  final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(noRollbackFor = ResponseStatusException.class)
    public void recuperarPassword(RecuperarPasswordRq rq) throws BadRequestException {

        String username = rq.getUsername();

        //Encontrar el Usuario
        Optional<Usuario> opt = usuarioRepository.findByUsername(username);

        //Para validar que el usuario no exista
        if (opt.isEmpty()) {
            auditoriaService.registrarError(
                    username,
                    "Usuario no encontrado en recuperacion de contraseña"
            );
            throw new ResponseStatusException(
                    HttpStatus.OK,
                    "Si el usuario existe,recibira un correo"
            );
        }

        Usuario user = opt.get();

        // Generar contraseña temporal
        String passwordTemporal = generarPasswordTemporal();

        // Actualizar contraseña
        user.setPasswordHash(passwordEncoder.encode(passwordTemporal));
        usuarioRepository.save(user);

        // Intentar enviar correo sin romper la transacción
        try {
            emailService.enviarCorreo(
                    user.getEmail(),
                    "Recuperación de Contraseña",
                    "Su nueva contraseña temporal es: " + passwordTemporal,
                    emailService.getTo()
            );
        } catch (Exception e) {
            auditoriaService.registrarError(
                    username,
                    "Error enviando correo: " + e.getMessage()
            );
            // NO hacemos throw para evitar rollback
        }

        // Registrar auditoría OK
        auditoriaService.registrarRecuperacion(user.getId().intValue(), username);
    }
    //Metodo privado para generar la clave temporal
    private String generarPasswordTemporal() {
        int length = 8;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }

        return password.toString();
    }

}
