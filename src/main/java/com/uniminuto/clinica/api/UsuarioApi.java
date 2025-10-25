package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Usuario;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * API para gestión de usuarios.
 *
 * @author lmora
 */
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public interface UsuarioApi {

    /**
     * Lista todos los usuarios.
     *
     * @return lista de usuarios
     */
    @RequestMapping(value = "/all",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Usuario>> listarUsuarios();

    /**
     * Busca usuario por nombre.
     *
     * @param username nombre de usuario
     * @return usuario encontrado
     * @throws BadRequestException si username inválido
     */
    @RequestMapping(value = "/find-username",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Usuario> encontrarUsuarioPorNombre(
            @RequestParam String username
    ) throws BadRequestException;

    /**
     * Busca usuarios por rol.
     *
     * @param role rol del usuario
     * @return lista de usuarios
     */
    @RequestMapping(value = "/find-by-role",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Usuario>> encontrarUsuariosPorRol(
            @RequestParam String role
    );

    /**
     * Busca usuario por documento.
     *
     * @param numeroDocumento número de documento
     * @return usuario encontrado
     * @throws BadRequestException si documento inválido
     */
    @RequestMapping(value = "/find-by-document",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Usuario> encontrarUsuarioPorDocumento(
            @RequestParam String numeroDocumento
    ) throws BadRequestException;
}