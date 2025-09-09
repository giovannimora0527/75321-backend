package com.uniminuto.clinica.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//Lo que vamos responder despues del Post
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitaRs {
    private Long id;
    private Long pacienteId;
    private String pacienteDocumento;
    private String pacienteNombre;
    private Long medicoId;
    private String medicoNombre;
    private LocalDateTime fechaHora;
    private String estado;
    private String motivo;
}
