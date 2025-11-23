package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.UsuarioRq;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;

/**
 *
 * @author lmora
 */
@CrossOrigin(origins = "*")
@RequestMapping("/usuario")
public interface UsuarioApi {

    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Usuario>> listarUsuarios();
    
    
    
    @RequestMapping(value = "/listar-rol",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Usuario>> listarUsuariosPorRol(
       @RequestParam String rol
    );
    
    
    @RequestMapping(value = "/buscar-nombre",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Usuario> buscarUsuarioPorNombre(
       @RequestParam String nombre
    ) throws BadRequestException;
    
    
    
    @RequestMapping(value = "/buscar-estado",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Usuario>> buscarUsuariosPorEstado(
       @RequestParam Integer activo
    ) throws BadRequestException;
    
    
    @RequestMapping(value = "/guardar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> guardarUsuario(
       @RequestBody UsuarioRq usuarioNuevo
    ) throws BadRequestException, MessagingException;

    /**
     * Actualizar usuario.
     * @param usuario UsuarioRq de entrada.
     * @return respuesta del servicio.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/actualizar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> actualizarrUsuario(
            @RequestBody UsuarioRq usuario
    ) throws BadRequestException;
}