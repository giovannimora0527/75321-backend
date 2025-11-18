package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.RecuperacionPasswordApi;
import com.uniminuto.clinica.model.RecuperarPasswordRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.RecuperacionPasswordService;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@AllArgsConstructor
public class RecuperacionPasswordApiController implements RecuperacionPasswordApi {

    private final RecuperacionPasswordService recuperacionPasswordService;
    @Override
    public ResponseEntity<RespuestaRs> recuperarPassword(RecuperarPasswordRq rq) throws BadRequestException, MatchException, MessagingException {


        // Lógica principal
        recuperacionPasswordService.recuperarPassword(rq);

        // Siempre devolvemos mensaje "seguro"
        RespuestaRs rs = new RespuestaRs();
        rs.setStatus(200);
        rs.setMensaje("Si el usuario existe, recibirá un correo");

        return ResponseEntity.ok(rs);
    }
}
