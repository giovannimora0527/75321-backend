package com.uniminuto.clinica.model;

public class RespuestaRs {
    private String mensaje;
    private Integer status;

    public RespuestaRs() { }

    public RespuestaRs(Integer status, String mensaje) {
        this.status = status;
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
