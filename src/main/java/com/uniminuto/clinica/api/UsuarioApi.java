package com.uniminuto.clinica.api;

import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.UsuarioRq;
import com.uniminuto.clinica.model.UsuarioRs;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Firma de EndPoint
@RequestMapping("/usuarios") //Inicio del Path
public interface UsuarioApi {
    //Hacer la Peticion GET
    @GetMapping("buscar-por-documento/{numeroDocumento}")
    UsuarioRs buscarUsuarioPorDocumento(@PathVariable String numeroDocumento);

    //Listar Todos los usuarios
    @GetMapping("all")
    List<UsuarioRs> listarTodos();

    //Guardar los Usuarios

    @RequestMapping(value = "/guardar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> guardarUsuario(
            @RequestBody UsuarioRq usuario
    ) throws BadRequestException;
}

//Endpoint
// http://localhost:8000/clinica/v1/usuarios/buscar-por-documento/1002003001