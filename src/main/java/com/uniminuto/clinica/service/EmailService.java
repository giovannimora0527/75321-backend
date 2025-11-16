package com.uniminuto.clinica.service;

import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;

public interface EmailService {
    /**
     * Servicio que envia un correo.
     * @param to Destinatario.
     * @param subject Asunto.
     * @param body Cuerpo del correo.
     * @throws BadRequestException excepcion.
     */
    void enviarCorreoSimple(String to, String subject, String body)
            throws BadRequestException;


    /**
     * Servicio que envia un correo.
     * @param to Destinatario.
     * @param subject Asunto.
     * @param body Cuerpo del correo.
     * @param from Remitente.
     * @throws BadRequestException excepcion.
     * @throws MessagingException excepcion.
     */
    void enviarCorreo(String to, String subject, String body, String from)
            throws BadRequestException, MessagingException;

    /**
     * Obtiene el remitente del correo.
     * @return Remitente.
     */
    String getTo();

    /**
     * Metodo para enviar correos html.
     * @param to destinatario.
     * @param subject asunto.
     * @param htmlBody cuerpo del correo.
     * @param from remitente.
     * @throws BadRequestException excepcion.
     * @throws MessagingException excepcion del servicio mail.
     */
    void sendHtmlEmail(String to,
                       String subject,
                       String htmlBody,
                       String from)
            throws BadRequestException, MessagingException;


    RespuestaRs testEmail(String correoDestinatario) throws BadRequestException, MessagingException;

    /**
     * Metodo para enviar correos con adjuntos.
     * @param to destinatario.
     * @param subject asunto.
     * @param body cuerpo del correo.
     * @param from remitente.
     * @param adjunto archivo adjunto.
     * @param filename nombre del archivo adjunto.
     * @throws BadRequestException excepcion.
     */
    void enviarCorreoConAdjuntos(String to, String subject, String body, String from,
                                 MultipartFile adjunto, String filename) throws BadRequestException;
}
