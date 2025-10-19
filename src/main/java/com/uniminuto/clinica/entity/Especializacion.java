package com.uniminuto.clinica.entity;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entidad que representa la tabla 'especializacion'.
 * 
 * Contiene la información de cada especialización registrada,
 * incluyendo nombre, descripción y un código único.
 */
@Entity
@Table(name = "especializacion")
@Data
public class Especializacion implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador único de la especialización.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Nombre de la especialización.
     */
    @Column(name="nombre", nullable = false, unique = true, length = 100)
    private String nombre;

    /**
     * Descripción detallada de la especialización.
     */
    @Column(name="descripcion")
    private String descripcion;

    /**
     * Código único para identificar la especialización.
     */
    @Column(name = "codigo_especializacion", nullable = false, unique = true, length = 10)
    private String codigoEspecializacion;
}
