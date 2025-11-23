package com.uniminuto.clinica.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Clase Entidad Medicamento.
 */
@Data
@Entity
@Table(name = "medicamento")
public class Medicamento implements Serializable {

    /**
     * Id serializable.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador único del medicamento.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    /**
     * Nombre del medicamento.
     */
    @Column(name = "nombre", length = 100, nullable = false, unique = true)
    private String nombre;

    /**
     * Descripción del medicamento.
     */
    @Column(name = "descripcion", columnDefinition = "text")
    private String descripcion;

    /**
     * Presentación del medicamento.
     */
    @Column(name = "presentacion", length = 100)
    private String presentacion;

    /**
     * Fecha de compra del medicamento.
     */
    @Column(name = "fecha_compra", nullable = false)
    private LocalDate fechaCompra;

    /*** Fecha de vencimiento del medicamento.
     */
    @Column(name = "fecha_vence", nullable = false)
    private LocalDate fechaVence;

    /**
     * Fecha de creación del registro.
     */
    @javax.persistence.Column(name = "fecha_creacion_registro", nullable = false)
    private LocalDateTime fechaCreacionRegistro;

    /**
     * Fecha de modificación del registro.
     */
    @javax.persistence.Column(name = "fecha_modificacion_registro")
    private LocalDateTime fechaModificacionRegistro;
}
