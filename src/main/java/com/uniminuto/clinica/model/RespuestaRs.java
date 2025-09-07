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

//Hay QUE CREAR UN MODELO PARA EL SGUNDO Punto

//para madnar un request y hay que probralo en Postamn en body

//guardar en la logica validar los datos que sean validos

//validar y reutilizar codigo