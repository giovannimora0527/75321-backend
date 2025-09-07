package com.uniminuto.clinica.model;

import lombok.Data;

import java.time.LocalDateTime;

//datos que vamos recibir para buscar
@Data
public class UsuarioRs {
    private Long id;
    private String username;
    private String rol;
    private LocalDateTime fechaCreacion;
    private Boolean activo;

}
//luego pasar a Repository