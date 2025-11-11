package com.uniminuto.clinica.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

@Data
public class MedicoRq {

    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombres;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellidos;

    @NotBlank(message = "El tipo de documento es obligatorio")
    private String tipoDocumento;

    @NotBlank(message = "El número de documento es obligatorio")
    private String documento;

    @NotNull(message = "La especialización es obligatoria")
    private Long especializacion;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    @NotBlank(message = "El registro profesional es obligatorio")
    private String registroProfesional;
}
