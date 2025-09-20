/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.clinica.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author johnq
 */
@Data
@Entity
@Table(name = "cita")
@AllArgsConstructor
@NoArgsConstructor
public class Cita {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // Relación con Paciente
    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false) // FK
    private Paciente paciente;

    // Relación con Medico
    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false) // FK
    private Medico medico;

    @Column()
    private LocalDateTime fechaHora;

    @Column(name = "estado", length = 20)
    private String estado;

    @Column()
    private String motivo;

}
