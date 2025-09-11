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

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UsuarioApiController {

    @Autowired
    private UsuarioService usuarioService;

   
    @GetMapping("/all")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarTodosLosUsuarios());
    }

    
    @GetMapping("/find-username")
    public ResponseEntity<Usuario> encontrarUsuarioPorNombre(@RequestParam String username)
            throws BadRequestException {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorNombre(username));
    }

  
    @GetMapping("/find-by-role")
    public ResponseEntity<List<Usuario>> encontrarUsuariosPorRol(@RequestParam String role) {
        return ResponseEntity.ok(usuarioService.buscarPorRol(role));
    }

    
    @GetMapping("/find-by-document")
    public ResponseEntity<Usuario> encontrarUsuarioPorDocumento(@RequestParam String numeroDocumento)
            throws BadRequestException {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorDocumento(numeroDocumento));
    }
}