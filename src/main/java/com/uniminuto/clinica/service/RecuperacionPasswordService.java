package com.uniminuto.clinica.service;

import com.uniminuto.clinica.model.RecuperarPasswordRq;
import org.apache.coyote.BadRequestException;

import javax.mail.MessagingException;

public interface RecuperacionPasswordService {

    //Firma del metodo para recuperar
    void recuperarPassword(RecuperarPasswordRq rq) throws BadRequestException, MessagingException;
}
