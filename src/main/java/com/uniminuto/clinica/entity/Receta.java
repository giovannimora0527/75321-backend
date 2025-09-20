/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.clinica.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author johnq
 */
@Data
@Entity
@Table(name = "receta")
public class Receta implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "cita_id")
    private Integer citaId;
    
    @Column(name = "medicamento_id")
    private Integer medicamentoId;
    
    @Column(name = "dosis")
    private String dosis;
    
    @Column(name = "indicaciones")
    private String indicaciones;
    
    @Column(name="fecha_creacion")
    private LocalDateTime fechaCreacion;
}
