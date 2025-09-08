/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.clinica.apicontroller;
import com.uniminuto.clinica.api.PacienteApi;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.service.PacienteService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 * @author jorge
 */
@RestController
public class PacienteApiController implements PacienteApi {
    
    @Autowired
    private PacienteService pacienteService;

    @Override
    public ResponseEntity<List<Paciente>> listarPacientes() {
        return ResponseEntity.ok(this.pacienteService.listarTodo());
    }

    /**
     *
     * @param numeroDocumento
     * @return
     * @throws BadRequestException
     */
    @Override
    public ResponseEntity<Paciente> listarPorDocumento(String numeroDocumento) 
    throws BadRequestException{
       return ResponseEntity.ok(this.pacienteService
               .obtenerPacientesPorDocumento(numeroDocumento));
        }
}
