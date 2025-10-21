package com.uniminuto.clinica.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class EspecializacionRq {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;

    private String descripcion;

    @NotBlank(message = "El código de especialización es obligatorio")
    @Size(max = 10, message = "El código no puede exceder 10 caracteres")
    private String codigoEspecializacion;
}