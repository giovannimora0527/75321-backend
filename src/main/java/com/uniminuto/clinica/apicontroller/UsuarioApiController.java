package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.UsuarioApi;
import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.UsuarioRq;
import com.uniminuto.clinica.service.UsuarioService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lmora
 */
@RestController
public class UsuarioApiController implements UsuarioApi {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(this.usuarioService.listarTodosLosUsuarios());
    }

    @Override
    public ResponseEntity<Usuario> encontrarUsuarioPorNombre(String username)
            throws BadRequestException {
        return ResponseEntity.ok(this.usuarioService
                .buscarUsuarioPorNombre(username));
    }

    @Override
    public ResponseEntity<List<Usuario>> encontrarUsuariosPorRol(String role) {
        return ResponseEntity.ok(this.usuarioService.buscarPorRol(role));
    }

    @Override
    public ResponseEntity<RespuestaRs> guardarUsuario(UsuarioRq usuario)
            throws BadRequestException {
        return ResponseEntity.ok(this.usuarioService.guardarUsuario(usuario));
    }

    @Override
    public ResponseEntity<RespuestaRs> actualizarUsuario(UsuarioRq usuario) throws BadRequestException {
        return ResponseEntity.ok(this.usuarioService.actualizarUsuario(usuario));
    }

}
