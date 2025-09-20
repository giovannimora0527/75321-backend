package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.entity.Usuario;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/pacientes")  // Cambiado para coincidir con el controlador
public interface PacienteApi {
    
    @RequestMapping(value = "/all",  // Cambiado para coincidir con el controlador
            produces = {"application/json"},
            method = RequestMethod.GET)  // Removido consumes ya que GET no envía body
    ResponseEntity<List<Paciente>> listarPaciente();

    @RequestMapping(value = "/find-documento",  // Cambiado para coincidir con el controlador
            produces = {"application/json"},
            method = RequestMethod.GET)  // Removido consumes ya que GET no envía body
    ResponseEntity<Paciente> buscarPorNumeroDocumento(  // Cambiado el tipo de retorno
            @RequestParam String numeroDocumento
    ) throws BadRequestException;
}