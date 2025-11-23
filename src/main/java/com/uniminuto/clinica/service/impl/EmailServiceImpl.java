package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.service.EmailService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.regex.Pattern;

/**
 * Implementación del servicio de envío de correos
 * @author CLINICA
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired(required = false)
    private JavaMailSender mailSender;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * Envía un correo de recuperación de contraseña con token
     */
    @Override
    public void enviarCorreoRecuperacion(String to, String token) throws BadRequestException {
        try {
            // Validar email
            if (!validarEmail(to)) {
                throw new BadRequestException("El email " + to + " no es válido");
            }

            // Validar que MailSender esté disponible
            if (mailSender == null) {
                System.err.println("✗ MailSender no está configurado en el servidor");
                throw new BadRequestException("El servicio de correo no está disponible");
            }

            // Crear el mensaje
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Recuperación de Contraseña - Sistema de Clínica");
            helper.setFrom("clinica.sistema@gmail.com");

            // URL del enlace de recuperación
            String url = "http://localhost:4200/reset-password?token=" + token;

            // Contenido HTML del correo
            String contenido = construirContenidoRecuperacion(url);

            helper.setText(contenido, true);

            // Enviar correo
            mailSender.send(mensaje);
            System.out.println("✓ Correo de recuperación enviado exitosamente a: " + to);

        } catch (BadRequestException e) {
            System.err.println("✗ Error de validación: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("✗ Error al enviar correo de recuperación a " + to + ": " + e.getMessage());
            e.printStackTrace();
            throw new BadRequestException("No se pudo enviar el correo de recuperación");
        }
    }

    /**
     * Valida que un email sea válido usando expresión regular
     */
    @Override
    public boolean validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Envía un correo genérico con asunto y contenido personalizados
     */
    @Override
    public void enviarCorreoGenerico(String to, String asunto, String contenido) throws BadRequestException {
        try {
            // Validar email
            if (!validarEmail(to)) {
                throw new BadRequestException("El email " + to + " no es válido");
            }

            // Validar que MailSender esté disponible
            if (mailSender == null) {
                System.err.println("✗ MailSender no está configurado en el servidor");
                throw new BadRequestException("El servicio de correo no está disponible");
            }

            // Validar parámetros
            if (asunto == null || asunto.trim().isEmpty()) {
                throw new BadRequestException("El asunto del correo es requerido");
            }

            if (contenido == null || contenido.trim().isEmpty()) {
                throw new BadRequestException("El contenido del correo es requerido");
            }

            // Crear el mensaje
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(asunto);
            helper.setFrom("clinica.sistema@gmail.com");
            helper.setText(contenido, true);

            // Enviar correo
            mailSender.send(mensaje);
            System.out.println("✓ Correo genérico enviado exitosamente a: " + to);

        } catch (BadRequestException e) {
            System.err.println("✗ Error de validación: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("✗ Error al enviar correo a " + to + ": " + e.getMessage());
            e.printStackTrace();
            throw new BadRequestException("No se pudo enviar el correo");
        }
    }

    /**
     * Construye el contenido HTML del correo de recuperación
     */
    private String construirContenidoRecuperacion(String url) {
        return "<html><body>" +
                "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;'>" +
                "<h2 style='color: #333;'>Recuperación de Contraseña</h2>" +
                "<p>Hola,</p>" +
                "<p>Has solicitado restablecer tu contraseña en el Sistema de Clínica.</p>" +
                "<p>Haz clic en el siguiente enlace para continuar:</p>" +
                "<p><a href='" + url + "' style='background-color: #007bff; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;'>Restablecer Contraseña</a></p>" +
                "<p style='color: #666; font-size: 12px;'>Este enlace expirará en 24 horas.</p>" +
                "<p style='color: #666; font-size: 12px;'>Si no solicitaste esto, ignora este correo.</p>" +
                "</div>" +
                "</body></html>";
    }
}
