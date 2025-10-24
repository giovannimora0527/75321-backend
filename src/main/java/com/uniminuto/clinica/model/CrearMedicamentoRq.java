package com.uniminuto.clinica.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

//Los Datos que se envian al Frontend
@Data
public class CrearMedicamentoRq {
    //Validadores
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La presentación es obligatoria")
    private String presentacion;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor que cero")
    private Integer cantidad;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    private LocalDate fechaVencimiento;

    private String descripcion;
}
