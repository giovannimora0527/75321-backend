package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.EmailApi;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.EmailService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class EmailApiController implements EmailApi {

    @Autowired
    private EmailService emailService;

    @Override
    public ResponseEntity<RespuestaRs> testEmail() throws BadRequestException, MessagingException {
        return ResponseEntity.ok(emailService.testEmail("giovannimora0527@gmail.com"));
    }
}
