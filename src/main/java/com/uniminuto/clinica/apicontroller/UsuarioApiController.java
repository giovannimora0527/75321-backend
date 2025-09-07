package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.UsuarioApi;
import com.uniminuto.clinica.model.UsuarioRs;
import com.uniminuto.clinica.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor //
public class UsuarioApiController  implements UsuarioApi {
    //Inyectar Usurio Service
    private final UsuarioService usuarioService;

    @Override
    public UsuarioRs buscarUsuarioPorDocumento(String numeroDocumento) {
        //Crear el objeto
        UsuarioRs dto=usuarioService.buscarUsuarioPorNumeroDocumento(numeroDocumento);
        if(dto==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Usuario no encontrado para documento%s",numeroDocumento));
        }
        return dto;
    }
}
