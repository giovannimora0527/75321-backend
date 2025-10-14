package com.uniminuto.clinica.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

//lo que va responder la Api
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacienteRs {
    private Long id;
    private String tipoDocumento;
    private String numeroDocumento;
    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String genero;
    private String telefono;
    private String direccion;

    // datos del usuario asociado
    private Long usuarioId;
    private String username;

}
