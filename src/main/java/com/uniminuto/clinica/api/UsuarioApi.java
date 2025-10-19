package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.UsuarioRq;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author lmora
 */
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public interface UsuarioApi {
    @GetMapping(value = "/all", produces = "application/json")
    ResponseEntity<List<Usuario>> listarUsuarios();

    @GetMapping(value = "/find-username", produces = "application/json")
    ResponseEntity<Usuario> encontrarUsuarioPorNombre(
            @RequestParam String username
    ) throws BadRequestException;

    @GetMapping(value = "/find-by-role", produces = "application/json")
    ResponseEntity<List<Usuario>> encontrarUsuariosPorRol(
            @RequestParam String role
    );

    @PostMapping(value = "/guardar", produces = "application/json", consumes = "application/json")
    ResponseEntity<RespuestaRs> guardarUsuario(
            @RequestBody UsuarioRq usuario
    ) throws BadRequestException;

    @PostMapping(value = "/actualizar", produces = "application/json", consumes = "application/json")
    ResponseEntity<RespuestaRs> actualizarUsuario(
            @RequestBody UsuarioRq usuario
    ) throws BadRequestException;

}
