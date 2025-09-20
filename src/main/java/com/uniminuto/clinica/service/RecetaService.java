/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Receta;
import java.util.List;

/**
 *
 * @author johnq
 */
public interface RecetaService {

    Receta guardar(Receta receta);

    List<Receta> listarRecetaPorFecha();
}
