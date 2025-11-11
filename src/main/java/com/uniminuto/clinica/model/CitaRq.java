package com.uniminuto.clinica.model;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class CitaRq {
    /**
     * Identificador del paciente asociado a la cita.
     */
    @NotNull(message = "El identificador del paciente es obligatorio.")
    private Integer pacienteId;

    /**
     * Identificador del médico que atenderá la cita.
     */
    @NotNull(message = "El identificador del médico es obligatorio.")
    private Integer medicoId;

    /**
     * Fecha y hora programada para la cita (formato ISO 8601)
     * yyyy-MM-dd HH:mm:ss.
     */
    @NotBlank(message = "La fecha y hora de la cita es obligatoria.")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$", message = "La fecha y hora debe tener el formato yyyy-MM-dd HH:mm:ss.")
    private String fechaHora;

    /**
     * Estado actual de la cita (por ejemplo: programada, cancelada, completada).
     */
    @NotBlank(message = "El estado de la cita es obligatorio.")
    private String estado;

    /**
     * Motivo o razón de la cita.
     */
    @NotBlank(message = "El motivo de la cita es obligatorio.")
    private String motivo;
}
