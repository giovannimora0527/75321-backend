package com.uniminuto.clinica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Value("${spring.mail.username}")
    private String emailFrom;
    
    @Value("${FRONTEND_URL:http://localhost:4200}")
    private String frontendUrl;
    
    /**
     * Envía email de recuperación de contraseña
     */
    public void enviarEmailRecuperacion(String destinatario, String token) throws MessagingException {
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");
        
        helper.setFrom(emailFrom);
        helper.setTo(destinatario);
        helper.setSubject("Recuperación de Contraseña - Sistema Clínica UNIMINUTO");
        
        String resetUrl = frontendUrl + "/reset-password?token=" + token;
        
        String contenidoHtml = "<!DOCTYPE html>" +
            "<html>" +
            "<head>" +
            "    <style>" +
            "        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }" +
            "        .container { max-width: 600px; margin: 0 auto; padding: 20px; }" +
            "        .header { background-color: #2196F3; color: white; padding: 20px; text-align: center; }" +
            "        .content { background-color: #f9f9f9; padding: 30px; border-radius: 5px; }" +
            "        .button { " +
            "            display: inline-block; " +
            "            background-color: #4CAF50; " +
            "            color: white; " +
            "            padding: 12px 30px; " +
            "            text-decoration: none; " +
            "            border-radius: 5px; " +
            "            margin: 20px 0;" +
            "        }" +
            "        .token-box { " +
            "            background-color: #fff3cd; " +
            "            border: 2px solid #ffc107; " +
            "            padding: 15px; " +
            "            margin: 20px 0; " +
            "            border-radius: 5px;" +
            "            font-family: monospace;" +
            "            word-break: break-all;" +
            "        }" +
            "        .warning { color: #d32f2f; font-weight: bold; }" +
            "        .footer { text-align: center; margin-top: 20px; font-size: 12px; color: #777; }" +
            "    </style>" +
            "</head>" +
            "<body>" +
            "    <div class='container'>" +
            "        <div class='header'>" +
            "            <h1> Sistema Clínica UNIMINUTO</h1>" +
            "        </div>" +
            "        <div class='content'>" +
            "            <h2>Recuperación de Contraseña</h2>" +
            "            <p>Hemos recibido una solicitud para restablecer tu contraseña.</p>" +
            "            " +
            "            <p><strong>Opción 1:</strong> Haz clic en el siguiente botón:</p>" +
            "            <a href='" + resetUrl + "' class='button'>Restablecer Contraseña</a>" +
            "            " +
            "            <p><strong>Opción 2:</strong> Copia y pega este enlace en tu navegador:</p>" +
            "            <div class='token-box'>" + resetUrl + "</div>" +
            "            " +
            "            <p class='warning'> IMPORTANTE:</p>" +
            "            <ul>" +
            "                <li>Este enlace <strong>expira en 24 horas</strong></li>" +
            "                <li>Si no solicitaste este cambio, ignora este correo</li>" +
            "                <li>Nunca compartas este enlace con nadie</li>" +
            "            </ul>" +
            "        </div>" +
            "        <div class='footer'>" +
            "            <p>Este es un correo automático, por favor no responder.</p>" +
            "            <p>&copy; 2025 Clínica UNIMINUTO - Todos los derechos reservados</p>" +
            "        </div>" +
            "    </div>" +
            "</body>" +
            "</html>";
        
        helper.setText(contenidoHtml, true);
        mailSender.send(mensaje);
        
        System.out.println("Email de recuperación enviado a: " + destinatario);
    }
    
    /**
     * Envía contraseña temporal al usuario
     */
    public void enviarPasswordTemporal(String destinatario, String nombreUsuario, String passwordTemporal) 
            throws MessagingException {
        
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");
        
        helper.setFrom(emailFrom);
        helper.setTo(destinatario);
        helper.setSubject("Contraseña Temporal - Sistema Clínica UNIMINUTO");
        
        String contenidoHtml = "<!DOCTYPE html>" +
            "<html>" +
            "<head>" +
            "    <style>" +
            "        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }" +
            "        .container { max-width: 600px; margin: 0 auto; padding: 20px; }" +
            "        .header { background-color: #4CAF50; color: white; padding: 20px; text-align: center; }" +
            "        .content { background-color: #f9f9f9; padding: 30px; border-radius: 5px; }" +
            "        .password-box { " +
            "            background-color: #fff3cd; " +
            "            border: 2px solid #ffc107; " +
            "            padding: 15px; " +
            "            margin: 20px 0; " +
            "            border-radius: 5px;" +
            "            text-align: center;" +
            "            font-size: 18px;" +
            "            font-weight: bold;" +
            "            font-family: monospace;" +
            "        }" +
            "        .warning { color: #d32f2f; font-weight: bold; }" +
            "        .footer { text-align: center; margin-top: 20px; font-size: 12px; color: #777; }" +
            "    </style>" +
            "</head>" +
            "<body>" +
            "    <div class='container'>" +
            "        <div class='header'>" +
            "            <h1> Sistema Clínica UNIMINUTO</h1>" +
            "        </div>" +
            "        <div class='content'>" +
            "            <h2>Hola, " + nombreUsuario + "</h2>" +
            "            <p>Se ha generado una <strong>contraseña temporal</strong> para tu cuenta.</p>" +
            "            " +
            "            <div class='password-box'>" +
            "                 " + passwordTemporal +
            "            </div>" +
            "            " +
            "            <p class='warning'> IMPORTANTE:</p>" +
            "            <ul>" +
            "                <li>Esta contraseña es temporal y <strong>expira en 24 horas</strong></li>" +
            "                <li>Deberás cambiarla en tu primer inicio de sesión</li>" +
            "                <li>Si no solicitaste esta contraseña, contacta al administrador inmediatamente</li>" +
            "            </ul>" +
            "            " +
            "            <p><strong>Pasos a seguir:</strong></p>" +
            "            <ol>" +
            "                <li>Inicia sesión con tu usuario y esta contraseña temporal</li>" +
            "                <li>El sistema te pedirá crear una nueva contraseña</li>" +
            "                <li>Elige una contraseña segura (mínimo 8 caracteres)</li>" +
            "            </ol>" +
            "        </div>" +
            "        <div class='footer'>" +
            "            <p>Este es un correo automático, por favor no responder.</p>" +
            "            <p>&copy; 2025 Clínica UNIMINUTO - Todos los derechos reservados</p>" +
            "        </div>" +
            "    </div>" +
            "</body>" +
            "</html>";
        
        helper.setText(contenidoHtml, true);
        mailSender.send(mensaje);
        
        System.out.println("Contraseña temporal enviada a: " + destinatario);
    }
    
    /**
     * Método genérico para enviar emails (opcional)
     */
    public void enviarEmail(String destinatario, String asunto, String contenido) 
            throws MessagingException {
        
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");
        
        helper.setFrom(emailFrom);
        helper.setTo(destinatario);
        helper.setSubject(asunto);
        helper.setText(contenido, true);
        
        mailSender.send(mensaje);
        
        System.out.println("Email enviado a: " + destinatario);
    }
    
    /**
     * MÉTODO LEGACY - Para compatibilidad con código anterior
     * Redirige a enviarEmailRecuperacion
     */
    @Deprecated
    public void sendPasswordResetEmail(String destinatario, String token) throws MessagingException {
        enviarEmailRecuperacion(destinatario, token);
    }
}
