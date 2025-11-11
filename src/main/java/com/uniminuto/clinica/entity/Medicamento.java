package com.uniminuto.clinica.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "medicamento")
public class Medicamento implements Serializable {

    private static  final long serialVersionUID=1;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "nombre",length = 100,nullable = false,unique = true)
    private String name;

    @Column(name="descripcion",columnDefinition = "text")
    private String descripcion;

    @Column(name="presentacion",length = 100)
    private String presentacion;

    //Nuevo campo mapeado
    @Column(name="fecha_compra",nullable = false)
    private LocalDate fechaCompra;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @Column(name="fecha_creacion_registro",nullable = false,updatable = false)
    private LocalDateTime fechaCreacionRegistro;

    @Column(name = "fecha_modificacion_registro")
    private LocalDateTime fechaModificacionRegistro;
}
