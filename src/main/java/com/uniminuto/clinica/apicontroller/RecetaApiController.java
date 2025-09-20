/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.RecetaApi;
import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.service.impl.RecetaServiceImpl;
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
public class RecetaApiController implements RecetaApi {

    @Autowired
    private RecetaServiceImpl service;

    @Override
    public ResponseEntity<List<Receta>> listar() {
        return ResponseEntity.ok(this.service.listarRecetaPorFecha());
    }

    @Override
    public ResponseEntity<Receta> agregar(Receta receta) {
        return new ResponseEntity(this.service.guardar(receta), HttpStatus.CREATED);
    }

}
