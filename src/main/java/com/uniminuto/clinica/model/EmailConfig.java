package com.uniminuto.clinica.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class EmailConfig {
    /**
     * Host del SMTP.
     */
    @Value("${spring.mail.host}")
    private String smtpHost;

    /**
     * Puerto del SMTP.
     */
    @Value("${spring.mail.port}")
    private String smtpPort;

    /**
     * Habilitar SSL.
     */
    @Value("${spring.mail.properties.mail.smtp.ssl.enable}")
    private String sslEnable;

    /**
     * Auth en el SMTP.
     */
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String smtpAuth;

    /**
     * TLS en el SMTP.
     */
    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String startTls;

    /**
     * Username de la cuenta del SMTP.
     */
    @Value("${spring.mail.username}")
    private String username;

    /**
     * Password del SMTP.
     */
    @Value("${spring.mail.password}")
    private String password;

    /**
     * To del SMTP.
     */
    @Value("${spring.mail.to}")
    private String to;
}
