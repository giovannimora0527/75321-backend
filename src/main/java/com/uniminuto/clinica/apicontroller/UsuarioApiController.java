package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.UsuarioApi;
import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.UsuarioRq;
import com.uniminuto.clinica.model.UsuarioRs;
import com.uniminuto.clinica.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor //
public class UsuarioApiController  implements UsuarioApi {
    //Inyectar Usurio Service
    private final UsuarioService usuarioService;

    @Override
    public UsuarioRs buscarUsuarioPorDocumento(String numeroDocumento) {
        //Crear el objeto
        UsuarioRs dto = usuarioService.buscarUsuarioPorNumeroDocumento(numeroDocumento);
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Usuario no encontrado para documento%s", numeroDocumento));
        }
        return dto;
    }

    @Override
    public List<UsuarioRs> listarTodos() {
        return usuarioService.listarTodosLosUsuarios()
                .stream()
                .map(this::toRs)
                .toList();
    }

    @Override
    public ResponseEntity<RespuestaRs> guardarUsuario(UsuarioRq usuario) throws BadRequestException {
       return ResponseEntity.ok(this.usuarioService.guardarUsuario(usuario));
    }

    private UsuarioRs toRs(Usuario u) {
        return new UsuarioRs(
                u.getId(),
                u.getUsername(),
                u.getRol(),             // si es enum: u.getRol().name()
                u.getFechaCreacion(),
                u.isActivo()
        );
    }
}
