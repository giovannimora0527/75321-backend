package com.uniminuto.clinica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.email.from:noreply@clinica.com}")
    private String fromEmail;

    @Value("${app.frontend.url:http://localhost:4200}")
    private String frontendUrl;

    public void sendPasswordResetEmail(String toEmail, String token) {
        try {System.out.println("=========================================");
            System.out.println("INICIANDO ENVÍO DE EMAIL");
            System.out.println("Destinatario: " + toEmail);
            System.out.println("Token: " + token);
            System.out.println("From: " + fromEmail);
            System.out.println("Frontend URL: " + frontendUrl);
            System.out.println("=========================================");

            String resetLink = frontendUrl + "/#/reset-password?token=" + token;

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Recuperación de Contraseña - Sistema Clínica");
            message.setText(
                    "Hola,\n\n"
                    + "Has solicitado restablecer tu contraseña en el Sistema de Gestión Clínica.\n\n"
                    + "Por favor, haz clic en el siguiente enlace para crear una nueva contraseña:\n"
                    + resetLink + "\n\n"
                    + "Este enlace expirará en 24 horas.\n\n"
                    + "Si no solicitaste este cambio, ignora este correo y tu contraseña permanecerá sin cambios.\n\n"
                    + "Saludos,\n"
                    + "Equipo de Soporte - Sistema de Gestión Clínica"
            );
            
            System.out.println("Enviando email...");
            mailSender.send(message);
            System.out.println("Email de recuperación enviado a: " + toEmail);
        } catch (Exception e) {
            System.err.println("Error al enviar email: " + e.getMessage());
            System.err.println("Destinatario: " + toEmail);
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("No se pudo enviar el correo de recuperación", e);
        }
    }
}
