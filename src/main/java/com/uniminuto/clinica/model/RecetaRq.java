package com.uniminuto.clinica.model;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class RecetaRq {
    @NotNull(message = "La cita es obligatoria")
    private Long citaId;

    @NotNull(message = "El medicamento es obligatorio")
    private Integer medicamentoId;

    @NotBlank(message = "La dosis es obligatoria")
    private String dosis;

    @NotBlank(message = "Las indicaciones son obligatorias")
    private String indicaciones;
}
