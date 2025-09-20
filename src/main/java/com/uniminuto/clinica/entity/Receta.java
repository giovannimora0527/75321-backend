package com.uniminuto.clinica.entity;

import javax.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "receta")
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cita_id", nullable = false)
    private Cita cita;

    @Column(name = "descripcion")
    private Long descripcion;

    @Column(name = "medicamento_id", nullable = false)
    private Integer medicamentoId;

    @Column(name = "dosis", columnDefinition = "TEXT", nullable = false)
    private String dosis;

    @Column(name = "indicaciones", columnDefinition = "TEXT")
    private String indicaciones;

    @Column(name = "fecha_creacion_registro")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
}
