package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.service.UsuarioService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para gestión de usuarios.
 *
 * @author lmora
 */
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UsuarioApiController {

    /**
     * Servicio para operaciones de usuario.
     */
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Lista todos los usuarios del sistema.
     *
     * @return lista de usuarios
     */
    @GetMapping("/all")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarTodosLosUsuarios());
    }

    /**
     * Busca usuario por nombre de usuario.
     *
     * @param username nombre de usuario
     * @return usuario encontrado
     * @throws BadRequestException si username inválido
     */
    @GetMapping("/find-username")
    public ResponseEntity<Usuario> encontrarUsuarioPorNombre(@RequestParam String username)
            throws BadRequestException {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorNombre(username));
    }

    /**
     * Busca usuarios por rol.
     *
     * @param role rol del usuario
     * @return lista de usuarios
     */
    @GetMapping("/find-by-role")
    public ResponseEntity<List<Usuario>> encontrarUsuariosPorRol(@RequestParam String role) {
        return ResponseEntity.ok(usuarioService.buscarPorRol(role));
    }

    /**
     * Busca usuario por documento.
     *
     * @param numeroDocumento número de documento
     * @return usuario encontrado
     * @throws BadRequestException si documento inválido
     */
    @GetMapping("/find-by-document")
    public ResponseEntity<Usuario> encontrarUsuarioPorDocumento(@RequestParam String numeroDocumento)
            throws BadRequestException {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorDocumento(numeroDocumento));
    }
}