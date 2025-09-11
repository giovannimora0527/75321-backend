/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.clinica.model;

/**
 *
 * @author USUARIO

/**
 *
 * @author lmora
 */
public class RespuestaRs {
    
    private String mensaje;
    private boolean estaFuncionando;
    private int status;
    
   
    public RespuestaRs() {
    }
    
   
    public RespuestaRs(String mensaje, boolean estaFuncionando, int status) {
        this.mensaje = mensaje;
        this.estaFuncionando = estaFuncionando;
        this.status = status;
    }
    
    
    public String getMensaje() {
        return mensaje;
    }
    
    public boolean isEstaFuncionando() {
        return estaFuncionando;
    }
    
    public boolean getEstaFuncionando() {
        return estaFuncionando;
    }
    
    public int getStatus() {
        return status;
    }
    
    
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public void setEstaFuncionando(boolean estaFuncionando) {
        this.estaFuncionando = estaFuncionando;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "RespuestaRs{" +
                "mensaje='" + mensaje + '\'' +
                ", estaFuncionando=" + estaFuncionando +
                ", status=" + status +
                '}';
    }
}

