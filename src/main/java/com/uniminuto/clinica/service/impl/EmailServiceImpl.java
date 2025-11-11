package com.uniminuto.clinica.service.impl;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.uniminuto.clinica.model.EmailConfig;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.EmailService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EmailServiceImpl implements EmailService {
    /**
     * Email Sender.
     */
    @Autowired
    private JavaMailSender mailSender;

    /**
     * Configuracion del SMTP.
     */
    @Autowired
    private EmailConfig emailConfig;

    @Override
    public void enviarCorreoSimple(final String to, final String subject,
                                   final String body)
            throws BadRequestException {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            message.setFrom(emailConfig.getTo());
            mailSender.send(message);
            System.out.println("Correo enviado exitosamente a: " + to);
        } catch (MailException e) {
            System.err.println("Error al enviar el correo: " + e.getMessage());
            throw new BadRequestException("Error. " + e.getMessage());
        }
    }

    @Override
    public String getTo() {
        return this.emailConfig.getTo();
    }

    @Override
    public void enviarCorreo(final String to,
                             final String subject,
                             final String body,
                             final String from)
            throws BadRequestException, MessagingException {
        try {
            this.sendHtmlEmail(to, subject, body, from);
        } catch (MailException e) {
            System.err.println("Error al enviar el correo: "
                    + e.getMessage());
            throw new BadRequestException("Error. " + e.getMessage());
        } catch (MessagingException ex) {
            Logger.getLogger(EmailServiceImpl
                    .class.getName()).log(Level.SEVERE, null, ex);
            throw new MessagingException("Error => " + ex.getMessage());
        }
    }

    /**
     * Envia un correo HTML.
     * @param to para Destinatario.
     * @param subject Asunto.
     * @param htmlBody Cuerpo del correo.
     * @param from Remitente del correo.
     * @throws MessagingException excepcion.
     */
    @Override
    public void sendHtmlEmail(final String to,
                              final String subject,
                              final String htmlBody,
                              final String from)
            throws MessagingException {

        // Configuración de las propiedades del servidor SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.host", emailConfig.getSmtpHost());
        properties.put("mail.smtp.port", emailConfig.getSmtpPort());
        properties.put("mail.smtp.auth", emailConfig.getSmtpAuth());
        properties.put("mail.smtp.starttls.enable", emailConfig.getStartTls());
        properties.put("mail.smtp.ssl.enable", emailConfig.getSslEnable());

        // Crear la sesión de correo con autenticación
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailConfig.getUsername(),
                        emailConfig.getPassword());
            }
        });

        // Crear el mensaje de correo
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to));
        message.setSubject(subject);

        // Establecer el cuerpo del mensaje en formato HTML
        message.setContent(htmlBody, "text/html; charset=utf-8");

        // Enviar el correo
        Transport.send(message);
        System.out.println("Correo enviado con éxito a " + to);
    }

    @Override
    public RespuestaRs testEmail(String correoDestinatario) throws BadRequestException, MessagingException {
        this.sendHtmlEmail(correoDestinatario, "Prueba de correo",
                "<h1>Este es un correo de prueba</h1>"
                + "<p>Enviado desde el servicio de email.</p>",
                this.getTo());
        RespuestaRs rta = new RespuestaRs();
        rta.setStatus(200);
        rta.setMensaje("Correo enviado exitosamente a " + correoDestinatario);
        return rta;
    }

    @Override
    public void enviarCorreoConAdjuntos(String to, String subject, String body, String from,
                                        MultipartFile adjunto, String filename) throws BadRequestException {
        try {
            // Configuración de las propiedades del servidor SMTP
            Properties properties = new Properties();
            properties.put("mail.smtp.host", emailConfig.getSmtpHost());
            properties.put("mail.smtp.port", emailConfig.getSmtpPort());
            properties.put("mail.smtp.auth", emailConfig.getSmtpAuth());
            properties.put("mail.smtp.starttls.enable", emailConfig.getStartTls());
            properties.put("mail.smtp.ssl.enable", emailConfig.getSslEnable());

            // Crear la sesión de correo con autenticación
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(emailConfig.getUsername(),
                            emailConfig.getPassword());
                }
            });

            MimeMessage message = new MimeMessage(session);
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            helper.setFrom(from);

            // Adjuntar el archivo recibido
            if (adjunto != null && !adjunto.isEmpty()) {
                helper.addAttachment(filename, new ByteArrayResource(adjunto.getBytes()));
            }

            mailSender.send(message);
            System.out.println("Correo con adjunto enviado exitosamente a: " + to);
        } catch (MailException | MessagingException e) {
            System.err.println("Error al enviar el correo con adjunto: " + e.getMessage());
            Logger.getLogger(EmailServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new BadRequestException("Error al enviar correo con adjunto. " + e.getMessage());
        } catch (IOException e) {
            Logger.getLogger(EmailServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new BadRequestException(e);
        }
    }
}
