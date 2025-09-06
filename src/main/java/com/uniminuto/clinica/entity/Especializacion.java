package com.uniminuto.clinica.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "especializacion")
public class Especializacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // Parametros para luego Ingresar a JpaRepository

    @Column(name="nombre",nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(name="descripcion",columnDefinition = "TEXT")
    private String descripcion;

    @Column(name="codigo_especializacion",nullable = false,unique = true,length =10)
    private String codigoEspecializacion;
}

