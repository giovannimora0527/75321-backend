package com.uniminuto.clinica.api;

import com.uniminuto.clinica.model.RespuestaRs;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.mail.MessagingException;

@CrossOrigin(origins = "*")
@RequestMapping("/email")
public interface EmailApi {

    @RequestMapping(value = "/test",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<RespuestaRs> testEmail() throws BadRequestException, MessagingException;
}
