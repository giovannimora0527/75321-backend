package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.EmailApi;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.EmailService;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@AllArgsConstructor
public class EmailApiController implements EmailApi {
    private  final EmailService emailService;

    @Override
    public ResponseEntity<RespuestaRs> testEmail() throws BadRequestException, MessagingException {
        //Devolver lo que declaramos en el servicio
        return ResponseEntity.ok(emailService.testEmail("samusplay08@gmail.com"));
    }
}
