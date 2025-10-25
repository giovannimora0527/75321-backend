package com.uniminuto.clinica.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CitaRq {

    @NotNull(message = "El paciente es obligatorio")
    private Long pacienteId;

    @NotNull(message = "El médico es obligatorio")
    private Long medicoId;

    @NotNull(message = "La fecha y hora son obligatorias")
    private String fechaHora;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    @NotBlank(message = "El motivo es obligatorio")
    private String motivo;
}
