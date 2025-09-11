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
 * Entidad que representa la tabla 'especializacion'.
 * 
 ¿
 */
@Entity
@Table(name = "especializacion")
@Data
public class Especializacion implements Serializable {

    private static final long serialVersionUID = 1L;

   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

  
    @Column(name="nombre", nullable = false, unique = true, length = 100)
    private String nombre;

  
    @Column(name="descripcion")
    private String descripcion;

  
    @Column(name = "codigo_especializacion", nullable = false, unique = true, length = 10)
    private String codigoEspecializacion;
}
