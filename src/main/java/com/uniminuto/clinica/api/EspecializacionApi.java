package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Especializacion;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin("*")

public interface EspecializacionApi {


    ResponseEntity<List<Especializacion>> listar();

    ResponseEntity<Especializacion> buscarPorCodigo(String codigo);

    ResponseEntity<Especializacion> crear(Especializacion especializacion);

    ResponseEntity<Especializacion> actualizar(Long id, Especializacion especializacion);
}
