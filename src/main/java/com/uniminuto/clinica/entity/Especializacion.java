
package com.uniminuto.clinica.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * Entidad que representa las especializaciones médicas disponibles.
 *
 * @author lmora
 */
@Entity
@Table(name = "especializacion")
@Data
public class Especializacion implements Serializable {

    /**
     * ID de serialización para la clase.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador único de la especialización.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Nombre de la especialización médica.
     */
    @Column(name="nombre", nullable = false, unique = true, length = 100)
    private String nombre;

    /**
     * Descripción detallada de la especialización.
     */
    @Column(name="descripcion")
    private String descripcion;

    /**
     * Código único identificador de la especialización.
     */
    @Column(name = "codigo_especializacion", nullable = false, unique = true, length = 10)
    private String codigoEspecializacion;
}