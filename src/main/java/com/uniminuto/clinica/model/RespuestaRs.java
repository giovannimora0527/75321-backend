package com.uniminuto.clinica.model;

import lombok.Data;

/**
 *
 * @author lmora
 */
@Data
public class RespuestaRs {    
    private String mensaje;
    private boolean estaFuncionando;
    private Integer status;
}
