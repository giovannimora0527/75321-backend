package com.uniminuto.clinica.api;

import com.uniminuto.clinica.model.CitaRs;
import com.uniminuto.clinica.model.CrearCitaRq;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

//firmamos el Endpoint
@RequestMapping("/citas") //base del path
public interface CitaApi {
    @PostMapping("/crear")//sub
    CitaRs crear(@Valid @RequestBody CrearCitaRq rq);
}
