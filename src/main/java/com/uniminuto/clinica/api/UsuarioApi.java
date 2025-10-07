package com.uniminuto.clinica.api;

import com.uniminuto.clinica.model.UsuarioRs;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
}

//Endpoint
// http://localhost:8000/clinica/v1/usuarios/buscar-por-documento/1002003001