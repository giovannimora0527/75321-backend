package com.uniminuto.clinica.model;

import lombok.Data;

import java.time.LocalDateTime;

//Enviamos lo que Angular necesita
@Data
public class AuditoriaRs {
    private Integer id;
    private Integer userId;
    private String usernameIngresado;
    private String descripcionError;
    private LocalDateTime fechaEvento;
}
