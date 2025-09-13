package com.uniminuto.clinica.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

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
}
