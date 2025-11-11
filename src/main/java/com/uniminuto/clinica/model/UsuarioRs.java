package com.uniminuto.clinica.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//datos que vamos recibir para buscar
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRs {
    private Long id;
    private String username;
    private String rol;
    private LocalDateTime fechaCreacion;
    private Boolean activo;
    private String email;

}
//luego pasar a Repository