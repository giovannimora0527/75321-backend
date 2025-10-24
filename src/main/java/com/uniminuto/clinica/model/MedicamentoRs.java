package com.uniminuto.clinica.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

//Lo que va responder el backend
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicamentoRs {
    private Long id;
    private String nombre;
    private String presentacion;
    private Integer cantidad;
    private LocalDate fechaVencimiento;
    private String descripcion;
}
