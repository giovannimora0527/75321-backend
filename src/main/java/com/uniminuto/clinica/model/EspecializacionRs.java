package com.uniminuto.clinica.model;

import lombok.Builder;
import lombok.Data;
//lo que responde el backend
@Data
@Builder
public class EspecializacionRs {
    private Long id;
    private String nombre;
    private String descripcion;
    private String codigoEspecializacion;
}
