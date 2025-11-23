package com.uniminuto.clinica.model;

public class AutenticatorRs {

    private String token;
    private String mensaje; // nuevo
    private String bloqueadoHasta; // nuevo, ISO string

    // Getters y setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getBloqueadoHasta() {
        return bloqueadoHasta;
    }

    public void setBloqueadoHasta(String bloqueadoHasta) {
        this.bloqueadoHasta = bloqueadoHasta;
    }
}