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

    public void setMensaje(String el_servicio_de_clinica_esta_funcionando_M) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setEstaFuncionando(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setStatus(int i) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
