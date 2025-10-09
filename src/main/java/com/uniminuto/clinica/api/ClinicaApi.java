package com.uniminuto.clinica.api;

import com.uniminuto.clinica.model.RespuestaRs;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author lmora
 */
@CrossOrigin(origins = "*")
@RequestMapping("/clinica")
public interface ClinicaApi {

    @RequestMapping(value = "/test",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<RespuestaRs> testService();

}
