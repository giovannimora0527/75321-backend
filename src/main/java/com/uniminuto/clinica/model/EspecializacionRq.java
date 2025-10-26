package com.uniminuto.clinica.model;

import lombok.Data;

/**
 * @author lmora
 */
@Data
public class EspecializacionRq { 
    private Long id;
    private String nombre;
    private String descripcion;
    private String codigoEspecializacion;
}
