/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.CitaApi;
import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.service.impl.CitaServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author johnq
 */
@RestController
public class CitaApiController implements CitaApi {

    @Autowired
    private CitaServiceImpl citaService;

    @Override
    public ResponseEntity<List<Cita>> listar() {
        return ResponseEntity.ok(this.citaService.listarCitasPorFecha());
    }

    @Override
    public ResponseEntity<Cita> agregar(Cita cita) {
        return new ResponseEntity(this.citaService.guardar(cita), HttpStatus.CREATED);
    }

}
