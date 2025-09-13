package com.uniminuto.clinica.api;

import com.uniminuto.clinica.model.CrearRecetaRq;
import com.uniminuto.clinica.model.RecetaRs;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.awt.*;

@RequestMapping(path = "/recetas",
        consumes = MediaType.APPLICATION_JSON_VALUE)
// RETORNAMOS EN UN JSON
public interface RecetaApi {
    //Implemtamos el METODO Sea post,get,delete o put

    @PostMapping(path = "/crear",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    RecetaRs crear(@Valid @RequestBody CrearRecetaRq rq);
    //Firmamos el ENDPOINT
}
